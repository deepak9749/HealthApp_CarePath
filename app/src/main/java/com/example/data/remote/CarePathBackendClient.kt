package com.example.data.remote

import android.util.Log
import com.example.domain.AIResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody.Companion.toRequestBody
import org.json.JSONArray
import org.json.JSONObject
import java.io.IOException
import java.net.ConnectException
import java.net.SocketTimeoutException
import java.net.UnknownHostException
import java.util.concurrent.TimeUnit

object CarePathBackendClient {
    private const val TAG = "CarePathAI"

    // 60-second timeouts to allow Render free tier instances to spin up on cold start
    private val client = OkHttpClient.Builder()
        .connectTimeout(60, TimeUnit.SECONDS)
        .readTimeout(60, TimeUnit.SECONDS)
        .writeTimeout(30, TimeUnit.SECONDS)
        .callTimeout(90, TimeUnit.SECONDS)
        .retryOnConnectionFailure(true)
        .build()

    private val JSON_MEDIA_TYPE = "application/json; charset=utf-8".toMediaType()

    // Primary deployed Render backend endpoint
    private const val PRIMARY_CHAT_ENDPOINT = "https://carepath-qw1h.onrender.com/api/chat"

    var lastConnectionError: String? = null
        private set

    /**
     * Sends conversation messages to the deployed CarePath Groq backend on Render.
     * Includes automatic retries for transient cold-start / wake-up delays.
     */
    suspend fun queryMedicalAI(
        conversationHistory: List<Pair<String, String>>, // role ("user" or "assistant") to content
        language: String
    ): AIResponse? = withContext(Dispatchers.IO) {
        lastConnectionError = null

        val payload = JSONObject().apply {
            put("language", language)
            val msgsArray = JSONArray()
            for ((role, text) in conversationHistory) {
                val msgObj = JSONObject().apply {
                    put("role", if (role == "user") "user" else "assistant")
                    put("content", text)
                }
                msgsArray.put(msgObj)
            }
            put("messages", msgsArray)
        }

        val requestJsonString = payload.toString()
        val requestBody = requestJsonString.toRequestBody(JSON_MEDIA_TYPE)

        val maxAttempts = 3
        var currentAttempt = 0

        while (currentAttempt < maxAttempts) {
            currentAttempt++
            val startTime = System.currentTimeMillis()
            Log.i(TAG, "[CarePathAI] --------------------------------------------------")
            Log.i(TAG, "[CarePathAI] Query started (Attempt $currentAttempt/$maxAttempts)")
            Log.i(TAG, "[CarePathAI] Target Endpoint: $PRIMARY_CHAT_ENDPOINT")
            Log.i(TAG, "[CarePathAI] Language: $language | Payload: $requestJsonString")

            try {
                val request = Request.Builder()
                    .url(PRIMARY_CHAT_ENDPOINT)
                    .post(requestBody)
                    .build()

                client.newCall(request).execute().use { response ->
                    val elapsedMs = System.currentTimeMillis() - startTime
                    val statusCode = response.code
                    Log.i(TAG, "[CarePathAI] HTTP response code: $statusCode (${response.message}) [took ${elapsedMs}ms]")

                    if (response.isSuccessful) {
                        val responseBody = response.body?.string().orEmpty()
                        Log.i(TAG, "[CarePathAI] HTTP 200 response body: ${responseBody.take(300)}")

                        if (responseBody.isNotBlank()) {
                            val json = JSONObject(responseBody)
                            // Support both 'reply' and 'response' keys from backend
                            val reply = json.optString("reply", json.optString("response", ""))

                            if (reply.isNotBlank()) {
                                val actionType = if (json.has("actionType") && !json.isNull("actionType")) json.getString("actionType") else null
                                val actionLabel = if (json.has("actionLabel") && !json.isNull("actionLabel")) json.getString("actionLabel") else null
                                val actionPayload = if (json.has("actionPayload") && !json.isNull("actionPayload")) json.getString("actionPayload") else null

                                Log.i(TAG, "[CarePathAI] Successfully parsed AI response: replyLength=${reply.length}, actionType=$actionType")
                                return@withContext AIResponse(
                                    replyText = reply,
                                    actionType = actionType,
                                    actionLabel = actionLabel,
                                    actionPayload = actionPayload
                                )
                            } else {
                                Log.w(TAG, "[CarePathAI] Response JSON had blank 'reply' and 'response' fields.")
                            }
                        }
                    } else {
                        val errorBody = response.body?.string().orEmpty()
                        lastConnectionError = "HTTP $statusCode from $PRIMARY_CHAT_ENDPOINT: $errorBody"
                        Log.w(TAG, "[CarePathAI] Server returned non-success HTTP $statusCode: $errorBody")

                        // If 502/503/504 (typical for Render spinning up from sleep), retry after brief delay
                        if ((statusCode == 502 || statusCode == 503 || statusCode == 504) && currentAttempt < maxAttempts) {
                            Log.w(TAG, "[CarePathAI] Render container may be warming up. Retrying in 2 seconds...")
                            delay(2000L)
                            return@use // continue while loop
                        }
                    }
                }
            } catch (e: Exception) {
                val elapsedMs = System.currentTimeMillis() - startTime
                val isTimeout = e is SocketTimeoutException
                val isConnect = e is ConnectException
                val isDns = e is UnknownHostException

                lastConnectionError = "${e.javaClass.simpleName}: ${e.message}"
                Log.e(TAG, "[CarePathAI] Connection attempt $currentAttempt failed after ${elapsedMs}ms: ${e.javaClass.simpleName} - ${e.message}", e)

                // If transient timeout/connect error and attempts remain, wait and retry
                if ((isTimeout || isConnect || isDns || e is IOException) && currentAttempt < maxAttempts) {
                    Log.w(TAG, "[CarePathAI] Transient network error encountered. Retrying in 2 seconds...")
                    delay(2000L)
                    continue
                }
            }
        }

        Log.e(TAG, "[CarePathAI] All $maxAttempts attempts failed. Returning null to trigger fallback. Last error: $lastConnectionError")
        null
    }
}
