package com.example

import android.app.Application
import androidx.activity.ComponentActivity
import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.test.core.app.ApplicationProvider
import com.example.data.local.DiagnosticBookingEntity
import com.example.data.model.DiagnosticBookingStatus
import com.example.ui.SmartHealthViewModel
import com.example.ui.screens.diagnostics.DiagnosticReportPdfGenerator
import com.example.ui.screens.diagnostics.DiagnosticSearchScreen
import com.example.ui.screens.diagnostics.DiagnosticTestBookingScreen
import com.example.ui.screens.health.HealthProfileScreen
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config
import java.io.File

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [36])
class DiagnosticBookingFlowTest {

    @get:Rule
    val composeTestRule = createAndroidComposeRule<ComponentActivity>()

    @Before
    fun setup() {
        val app = ApplicationProvider.getApplicationContext<Application>()
        val db = com.example.data.local.AppDatabase.getDatabase(app)
        runBlocking(kotlinx.coroutines.Dispatchers.IO) {
            db.clearAllTables()
            com.example.data.datasource.DemoDataSeeder.seedDatabaseIfEmpty(db.smartHealthDao())
        }
    }

    @Test
    fun testDiagnosticSearchScreenCardClickable() {
        val app = ApplicationProvider.getApplicationContext<Application>()
        val viewModel = SmartHealthViewModel(app)

        var selectedTestId: String? = null

        composeTestRule.setContent {
            DiagnosticSearchScreen(
                viewModel = viewModel,
                onBack = {},
                onSelectTest = { selectedTestId = it }
            )
        }

        // Verify search screen is displayed
        composeTestRule.onNodeWithTag("diagnostic_search_screen").assertIsDisplayed()

        // Click CBC test card
        composeTestRule.onNodeWithTag("test_card_diag_1").performClick()
        composeTestRule.waitForIdle()

        // Verify navigation callback received test id
        assertEquals("diag_1", selectedTestId)
    }

    @Test
    fun testGuestModeBlocksBookingAndShowsAuthPrompt() {
        val app = ApplicationProvider.getApplicationContext<Application>()
        val viewModel = SmartHealthViewModel(app)

        // Ensure logged out (guest)
        runBlocking {
            viewModel.logout()
        }

        composeTestRule.setContent {
            DiagnosticTestBookingScreen(
                viewModel = viewModel,
                testId = "diag_2",
                onBack = {}
            )
        }

        // Screen is displayed
        composeTestRule.onNodeWithTag("diagnostic_test_booking_screen").assertIsDisplayed()

        // Filter by Government
        composeTestRule.onNodeWithTag("filter_facility_government").performClick()
        composeTestRule.waitForIdle()

        // Open Dropdown & select a government lab
        composeTestRule.onNodeWithTag("select_facility_dropdown").performClick()
        composeTestRule.waitForIdle()
        composeTestRule.onNodeWithText("Government General Hospital").performClick()
        composeTestRule.waitForIdle()

        // Scroll to date picker and select date
        composeTestRule.onNodeWithTag("diagnostic_test_booking_screen")
            .performScrollToNode(hasTestTag("select_date_button"))
        composeTestRule.onNodeWithTag("select_date_button").performClick()
        composeTestRule.waitForIdle()
        composeTestRule.onNodeWithTag("date_picker_confirm").performClick()
        composeTestRule.waitForIdle()

        // Scroll to and tap BOOK as a Guest
        composeTestRule.onNodeWithTag("diagnostic_test_booking_screen")
            .performScrollToNode(hasTestTag("diagnostic_book_button"))
        composeTestRule.onNodeWithTag("diagnostic_book_button").performClick()
        composeTestRule.waitForIdle()

        // Assert Auth required dialog is displayed
        composeTestRule.onNodeWithTag("auth_prompt_login_button").assertIsDisplayed()
        composeTestRule.onNodeWithText("You need an account to book a diagnostic test and track your booking status.").assertIsDisplayed()
    }

    @Test
    fun testLoggedInUserBookingAndStatusProgression() {
        val app = ApplicationProvider.getApplicationContext<Application>()
        val viewModel = SmartHealthViewModel(app)

        // Create logged-in user
        runBlocking {
            val appInstance = app as com.example.SmartHealthApp
            appInstance.repository.registerUser("Deepak Mandal", "deepak@example.com", "9876543210", false)
        }

        composeTestRule.setContent {
            DiagnosticTestBookingScreen(
                viewModel = viewModel,
                testId = "diag_1",
                onBack = {}
            )
        }

        // Filter by Private
        composeTestRule.onNodeWithTag("filter_facility_private").performClick()
        composeTestRule.waitForIdle()

        // Select Private Lab
        composeTestRule.onNodeWithTag("select_facility_dropdown").performClick()
        composeTestRule.waitForIdle()
        composeTestRule.onNodeWithText("Peerless Diagnostic Centre").performClick()
        composeTestRule.waitForIdle()

        // Scroll to and select date
        composeTestRule.onNodeWithTag("diagnostic_test_booking_screen")
            .performScrollToNode(hasTestTag("select_date_button"))
        composeTestRule.onNodeWithTag("select_date_button").performClick()
        composeTestRule.waitForIdle()
        composeTestRule.onNodeWithTag("date_picker_confirm").performClick()
        composeTestRule.waitForIdle()

        // Scroll to and tap BOOK as Authenticated user
        composeTestRule.onNodeWithTag("diagnostic_test_booking_screen")
            .performScrollToNode(hasTestTag("diagnostic_book_button"))
        composeTestRule.onNodeWithTag("diagnostic_book_button").performClick()
        composeTestRule.waitForIdle()

        // BOOK button disappears, Waiting for Confirmation banner appears
        composeTestRule.onNodeWithTag("diagnostic_book_button").assertDoesNotExist()
        composeTestRule.onNodeWithTag("booking_status_banner").assertIsDisplayed()
        composeTestRule.onNodeWithText("Waiting for Confirmation...").assertIsDisplayed()

        // Download Report button must NOT exist while Waiting for Confirmation
        composeTestRule.onNodeWithTag("download_report_button").assertDoesNotExist()

        // Tap status banner to open tracker modal
        composeTestRule.onNodeWithTag("booking_status_banner").performClick()
        composeTestRule.waitForIdle()

        // Modal displays vertical timeline steps
        composeTestRule.onNodeWithText("Booking Status").assertIsDisplayed()
        composeTestRule.onNodeWithText("Request Submitted").assertIsDisplayed()
        composeTestRule.onNodeWithText("Waiting for Confirmation").assertIsDisplayed()
        composeTestRule.onNodeWithText("Confirmed").assertIsDisplayed()

        // Close modal
        composeTestRule.onNodeWithText("Close").performClick()
        composeTestRule.waitForIdle()
    }

    @Test
    fun testPdfReportGeneration() {
        val app = ApplicationProvider.getApplicationContext<Application>()

        val booking = DiagnosticBookingEntity(
            bookingId = "CP-DIAG-TEST99",
            userId = "user_test",
            patientName = "Deepak Mandal",
            testId = "diag_1",
            testName = "Complete Blood Count (CBC) with ESR",
            facilityId = "pvt_1",
            facilityName = "Peerless Diagnostic Centre",
            facilityType = "Private",
            phoneNumber = "033-4011-1222",
            bookingDate = "15 Oct 2026",
            prescriptionFileName = "CBC_Prescription.jpg",
            requestCreatedAt = System.currentTimeMillis() - 70_000L,
            status = DiagnosticBookingStatus.CONFIRMED
        )

        // Generate PDF
        DiagnosticReportPdfGenerator.generateAndOpenReport(app, booking)

        // Verify PDF file was created on disk in cacheDir
        val expectedFile = File(app.cacheDir, "CarePath_Diagnostic_Booking_${booking.bookingId}.pdf")
        assertTrue("PDF report file should exist", expectedFile.exists())
        assertTrue("PDF report size should be > 0 bytes", expectedFile.length() > 0)
    }

    @Test
    fun testProfileLogoutVisibility() {
        val app = ApplicationProvider.getApplicationContext<Application>()
        val viewModel = SmartHealthViewModel(app)

        // 1. Logged out state (Guest)
        runBlocking {
            viewModel.logout()
            val appInstance = app as com.example.SmartHealthApp
            while (appInstance.repository.currentUser.first() != null) {
                delay(20)
            }
        }

        composeTestRule.setContent {
            HealthProfileScreen(
                viewModel = viewModel,
                onBack = {},
                onNavigateTo = {}
            )
        }

        // In Guest Mode, Log Out button should NOT exist
        composeTestRule.onNodeWithTag("profile_logout_button").assertDoesNotExist()
        composeTestRule.onNodeWithText("Create Account / Sign In").assertIsDisplayed()

        // 2. Log in
        runBlocking {
            val appInstance = app as com.example.SmartHealthApp
            appInstance.repository.registerUser("Deepak Mandal", "deepak@example.com", "9876543210", false)
        }

        composeTestRule.waitUntil(5000) {
            composeTestRule.onAllNodesWithTag("profile_logout_button").fetchSemanticsNodes().isNotEmpty()
        }

        // In Logged in mode, Log Out button must exist
        composeTestRule.onNodeWithTag("profile_logout_button").assertIsDisplayed()

        // Tap Log Out
        composeTestRule.onNodeWithTag("profile_logout_button").performClick()

        composeTestRule.waitUntil(5000) {
            composeTestRule.onAllNodesWithTag("profile_logout_button").fetchSemanticsNodes().isEmpty()
        }

        // After Logout, user returns to Guest Mode, Log Out button disappears
        composeTestRule.onNodeWithTag("profile_logout_button").assertDoesNotExist()
        composeTestRule.onNodeWithText("Create Account / Sign In").assertIsDisplayed()
    }
}
