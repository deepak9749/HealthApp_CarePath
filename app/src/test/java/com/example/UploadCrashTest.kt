package com.example

import android.app.Application
import androidx.activity.ComponentActivity
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.test.core.app.ApplicationProvider
import com.example.ui.SmartHealthViewModel
import com.example.ui.screens.documents.MedicalReportScreen
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [36])
class UploadCrashTest {

    @get:Rule
    val composeTestRule = createAndroidComposeRule<ComponentActivity>()

    @Test
    fun testClickUploadReportOpensDialogWithoutCrash() {
        val app = ApplicationProvider.getApplicationContext<Application>()
        val viewModel = SmartHealthViewModel(app)

        composeTestRule.setContent {
            MedicalReportScreen(
                viewModel = viewModel,
                onBack = {}
            )
        }

        // Tap "+ Upload Report" FAB
        composeTestRule.onNodeWithTag("upload_report_fab").performClick()
        composeTestRule.waitForIdle()

        // Verify the dialog is displayed safely
        composeTestRule.onNodeWithText("Upload Medical Document").assertIsDisplayed()
        composeTestRule.onNodeWithText("Document Category").assertIsDisplayed()
        composeTestRule.onNodeWithTag("choose_file_button").assertIsDisplayed()
        composeTestRule.onNodeWithTag("take_photo_button").assertIsDisplayed()
        composeTestRule.onNodeWithTag("confirm_upload_button").assertIsDisplayed()

        // Dismiss dialog
        composeTestRule.onNodeWithTag("cancel_upload_button").performClick()
        composeTestRule.waitForIdle()

        // Re-open via TopAppBar button
        composeTestRule.onNodeWithTag("upload_report_top_button").performClick()
        composeTestRule.waitForIdle()

        // Verify again
        composeTestRule.onNodeWithText("Upload Medical Document").assertIsDisplayed()
    }
}
