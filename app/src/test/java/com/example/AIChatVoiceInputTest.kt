package com.example

import android.app.Application
import androidx.activity.ComponentActivity
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextInput
import androidx.test.core.app.ApplicationProvider
import com.example.ui.SmartHealthViewModel
import com.example.ui.screens.ai.AIChatBottomSheet
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [36])
class AIChatVoiceInputTest {

    @get:Rule
    val composeTestRule = createAndroidComposeRule<ComponentActivity>()

    @Test
    fun testVoiceInputButtonAndTypingInteractions() {
        val app = ApplicationProvider.getApplicationContext<Application>()
        val viewModel = SmartHealthViewModel(app)

        composeTestRule.setContent {
            AIChatBottomSheet(
                viewModel = viewModel,
                onNavigateTo = {},
                onDismiss = {}
            )
        }

        // Verify existing chat input field is displayed
        composeTestRule.onNodeWithTag("ai_chat_input_field").assertIsDisplayed()

        // Verify circular microphone button is displayed in idle state
        composeTestRule.onNodeWithTag("ai_chat_mic_button").assertIsDisplayed()
        composeTestRule.onNodeWithContentDescription("Start voice input").assertIsDisplayed()

        // Verify send button is displayed
        composeTestRule.onNodeWithTag("ai_chat_send_button").assertIsDisplayed()

        // Test normal typing functionality into existing text input box
        composeTestRule.onNodeWithTag("ai_chat_input_field").performTextInput("I have a headache since yesterday")
        composeTestRule.waitForIdle()

        // Tap microphone button - should handle permission / toggle without crash
        composeTestRule.onNodeWithTag("ai_chat_mic_button").performClick()
        composeTestRule.waitForIdle()

        // Tapping again should stop cleanly without crashing
        composeTestRule.onNodeWithTag("ai_chat_mic_button").performClick()
        composeTestRule.waitForIdle()
    }
}
