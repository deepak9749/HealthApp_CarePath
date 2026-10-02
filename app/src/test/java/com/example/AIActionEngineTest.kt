package com.example

import com.example.domain.AIActionEngine
import org.junit.Assert.*
import org.junit.Test

class AIActionEngineTest {

    @Test
    fun testDirectCarePathFeatureIntentRouting() {
        // Feature requests should be routed directly to CarePath actions
        assertTrue(AIActionEngine.isCarePathFeatureRequest("I need a hospital near me"))
        assertTrue(AIActionEngine.isCarePathFeatureRequest("Find hospitals near me"))
        assertTrue(AIActionEngine.isCarePathFeatureRequest("Show me nearby hospitals"))
        assertTrue(AIActionEngine.isCarePathFeatureRequest("I need emergency help"))
        assertTrue(AIActionEngine.isCarePathFeatureRequest("Check my symptoms"))
        assertTrue(AIActionEngine.isCarePathFeatureRequest("I want to check my symptoms"))
        assertTrue(AIActionEngine.isCarePathFeatureRequest("I need a doctor consultation"))
        assertTrue(AIActionEngine.isCarePathFeatureRequest("Request Emergency Ambulance"))
        assertTrue(AIActionEngine.isCarePathFeatureRequest("Find ICU beds near me"))
        assertTrue(AIActionEngine.isCarePathFeatureRequest("Show Government hospitals"))
        assertTrue(AIActionEngine.isCarePathFeatureRequest("Book a doctor appointment"))
        assertTrue(AIActionEngine.isCarePathFeatureRequest("Check my symptoms (Triage)"))
        assertTrue(AIActionEngine.isCarePathFeatureRequest("Search medicines in pharmacy"))
    }

    @Test
    fun testConversationalMedicalQuestionsNotDirectFeatures() {
        // Natural language questions & greetings must NOT be captured as direct actions;
        // they should flow to conversational Groq AI with NO forced action
        assertFalse(AIActionEngine.isCarePathFeatureRequest("Hello"))
        assertFalse(AIActionEngine.isCarePathFeatureRequest("How are you?"))
        assertFalse(AIActionEngine.isCarePathFeatureRequest("What can you do?"))
        assertFalse(AIActionEngine.isCarePathFeatureRequest("Tell me something interesting"))
        assertFalse(AIActionEngine.isCarePathFeatureRequest("What is fever?"))
        assertFalse(AIActionEngine.isCarePathFeatureRequest("I have a headache since yesterday."))
        assertFalse(AIActionEngine.isCarePathFeatureRequest("I have fever and body pain. What should I do?"))
        assertFalse(AIActionEngine.isCarePathFeatureRequest("What can I do for acidity?"))
        assertFalse(AIActionEngine.isCarePathFeatureRequest("I have a cough and sore throat."))
        assertFalse(AIActionEngine.isCarePathFeatureRequest("What are the warning signs that mean I should go to a hospital?"))
        assertFalse(AIActionEngine.isCarePathFeatureRequest("What should I do for menstrual cramps?"))
        assertFalse(AIActionEngine.isCarePathFeatureRequest("I am feeling dizzy. What should I do?"))
        assertFalse(AIActionEngine.isCarePathFeatureRequest("Can you explain my symptoms?"))
        assertFalse(AIActionEngine.isCarePathFeatureRequest("What should I do first?"))
        assertFalse(AIActionEngine.isCarePathFeatureRequest("What are the warning signs of pneumonia?"))
    }

    @Test
    fun testDirectFeatureQueryResponses() {
        val ambResponse = AIActionEngine.processDirectFeatureQuery("call ambulance", "EN")
        assertEquals("NAV_AMBULANCE", ambResponse.actionType)

        val emerResponse = AIActionEngine.processDirectFeatureQuery("emergency sos", "EN")
        assertEquals("NAV_EMERGENCY", emerResponse.actionType)

        val icuResponse = AIActionEngine.processDirectFeatureQuery("find icu beds", "EN")
        assertEquals("NAV_HOSPITALS_ICU", icuResponse.actionType)

        val docResponse = AIActionEngine.processDirectFeatureQuery("book doctor appointment", "EN")
        assertEquals("NAV_DOCTORS", docResponse.actionType)

        val triageResponse = AIActionEngine.processDirectFeatureQuery("start triage assessment", "EN")
        assertEquals("NAV_SYMPTOMS", triageResponse.actionType)
    }

    @Test
    fun testEmergencySymptomsDetection() {
        assertTrue(AIActionEngine.hasEmergencySymptoms("I have severe chest pain"))
        assertTrue(AIActionEngine.hasEmergencySymptoms("Patient is unconscious and having difficulty breathing"))
        assertTrue(AIActionEngine.hasEmergencySymptoms("I am having severe chest pain and difficulty breathing"))
        assertTrue(AIActionEngine.hasEmergencySymptoms("বুকে প্রচণ্ড ব্যথা")) // Bengali chest pain
        assertTrue(AIActionEngine.hasEmergencySymptoms("छाती में दर्द")) // Hindi chest pain

        assertFalse(AIActionEngine.hasEmergencySymptoms("I have a mild runny nose"))
        assertFalse(AIActionEngine.hasEmergencySymptoms("What can I eat for acidity?"))
    }

    @Test
    fun testMultilingualFallbackResponses() {
        val enFallback = AIActionEngine.getFallbackMedicalResponse("EN", false)
        assertNotNull(enFallback.replyText)
        // Non-emergency fallback must NEVER force Check Symptoms action button
        assertNull(enFallback.actionType)
        assertNull(enFallback.actionLabel)

        val hiFallback = AIActionEngine.getFallbackMedicalResponse("HI", false)
        assertTrue(hiFallback.replyText.contains("CarePath AI"))
        assertNull(hiFallback.actionType)

        val bnFallback = AIActionEngine.getFallbackMedicalResponse("BN", false)
        assertTrue(bnFallback.replyText.contains("CarePath"))
        assertNull(bnFallback.actionType)

        val emergencyFallback = AIActionEngine.getFallbackMedicalResponse("EN", true)
        assertEquals("NAV_EMERGENCY", emergencyFallback.actionType)
    }
}
