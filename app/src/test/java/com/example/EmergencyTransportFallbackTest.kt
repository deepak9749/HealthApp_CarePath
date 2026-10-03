package com.example

import android.app.Application
import android.content.Intent
import android.content.pm.ActivityInfo
import android.content.pm.ApplicationInfo
import android.content.pm.PackageInfo
import android.content.pm.ResolveInfo
import androidx.activity.ComponentActivity
import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.test.core.app.ApplicationProvider
import com.example.ui.SmartHealthViewModel
import com.example.ui.screens.emergency.EmergencyCenterScreen
import com.example.ui.screens.emergency.EmergencyTransportLauncher
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.Shadows.shadowOf
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [36])
class EmergencyTransportFallbackTest {

    @get:Rule
    val composeTestRule = createAndroidComposeRule<ComponentActivity>()

    private lateinit var app: Application
    private lateinit var viewModel: SmartHealthViewModel

    @Before
    fun setup() {
        app = ApplicationProvider.getApplicationContext<Application>()
        viewModel = SmartHealthViewModel(app)
    }

    @Test
    fun testAmbulanceButtonPreservedAndFunctional() {
        var navigatedRoute: String? = null

        composeTestRule.setContent {
            EmergencyCenterScreen(
                viewModel = viewModel,
                onNavigateTo = { navigatedRoute = it },
                onBack = {}
            )
        }

        // Verify primary ambulance button exists and is prominently displayed
        composeTestRule.onNodeWithTag("request_ambulance_sos_button").assertIsDisplayed()
        composeTestRule.onNodeWithText("Dispatch ICU Ambulance to My Location").assertIsDisplayed()

        // Tap ambulance button
        composeTestRule.onNodeWithTag("request_ambulance_sos_button").performClick()
        composeTestRule.waitForIdle()

        // Confirmation dialog appears
        composeTestRule.onNodeWithText("Confirm Ambulance Dispatch").assertIsDisplayed()
        composeTestRule.onNodeWithText("Dispatch Now").assertIsDisplayed()

        // Confirm ambulance dispatch
        composeTestRule.onNodeWithText("Dispatch Now").performClick()
        composeTestRule.waitForIdle()

        assertEquals("ambulance", navigatedRoute)
    }

    @Test
    fun testAlternativeTransportButtonsExistBelowAmbulance() {
        composeTestRule.setContent {
            EmergencyCenterScreen(
                viewModel = viewModel,
                onNavigateTo = {},
                onBack = {}
            )
        }

        // Scroll to alternative transport section
        composeTestRule.onNodeWithTag("emergency_center_screen")
            .performScrollToNode(hasTestTag("alternative_transport_section"))

        // Verify section header & disclaimer
        composeTestRule.onNodeWithTag("alternative_transport_section").assertIsDisplayed()
        composeTestRule.onNodeWithText("Need Alternative Transport?").assertIsDisplayed()
        composeTestRule.onNodeWithText("If an ambulance is unavailable, you may arrange alternative transport. For life-threatening emergencies, use emergency medical services whenever possible.").assertIsDisplayed()

        // Verify both fallback buttons exist
        composeTestRule.onNodeWithTag("rapido_fallback_button").assertIsDisplayed()
        composeTestRule.onNodeWithText("Book with Rapido").assertIsDisplayed()

        composeTestRule.onNodeWithTag("uber_fallback_button").assertIsDisplayed()
        composeTestRule.onNodeWithText("Ride with Uber").assertIsDisplayed()
    }

    @Test
    fun testRapidoNotInstalledShowsDialogAndInstallsSafely() {
        // Ensure Rapido is not installed
        val shadowPm = shadowOf(app.packageManager)
        shadowPm.deletePackage(EmergencyTransportLauncher.RAPIDO_PACKAGE)

        composeTestRule.setContent {
            EmergencyCenterScreen(
                viewModel = viewModel,
                onNavigateTo = {},
                onBack = {}
            )
        }

        // Scroll to and tap Rapido fallback button
        composeTestRule.onNodeWithTag("emergency_center_screen")
            .performScrollToNode(hasTestTag("rapido_fallback_button"))
        composeTestRule.onNodeWithTag("rapido_fallback_button").performClick()
        composeTestRule.waitForIdle()

        // Assert not installed dialog appears
        composeTestRule.onNodeWithText("Rapido is not installed").assertIsDisplayed()
        composeTestRule.onNodeWithText("You can install Rapido to book an alternative ride.").assertIsDisplayed()
        composeTestRule.onNodeWithTag("install_rapido_button").assertIsDisplayed()

        // Tap Install Rapido -> should open store safely without crashing
        composeTestRule.onNodeWithTag("install_rapido_button").performClick()
        composeTestRule.waitForIdle()

        // Dialog dismissed
        composeTestRule.onNodeWithTag("install_rapido_button").assertDoesNotExist()

        val nextIntent = shadowOf(app).nextStartedActivity
        assertNotNull("Play store intent should be triggered", nextIntent)
        assertTrue(nextIntent.dataString?.contains(EmergencyTransportLauncher.RAPIDO_PACKAGE) == true)
    }

    @Test
    fun testUberNotInstalledShowsDialogAndInstallsSafely() {
        // Ensure Uber is not installed
        val shadowPm = shadowOf(app.packageManager)
        shadowPm.deletePackage(EmergencyTransportLauncher.UBER_PACKAGE)

        composeTestRule.setContent {
            EmergencyCenterScreen(
                viewModel = viewModel,
                onNavigateTo = {},
                onBack = {}
            )
        }

        // Scroll to and tap Uber fallback button
        composeTestRule.onNodeWithTag("emergency_center_screen")
            .performScrollToNode(hasTestTag("uber_fallback_button"))
        composeTestRule.onNodeWithTag("uber_fallback_button").performClick()
        composeTestRule.waitForIdle()

        // Assert not installed dialog appears
        composeTestRule.onNodeWithText("Uber is not installed").assertIsDisplayed()
        composeTestRule.onNodeWithText("You can install Uber to book an alternative ride.").assertIsDisplayed()
        composeTestRule.onNodeWithTag("install_uber_button").assertIsDisplayed()

        // Tap Install Uber -> should open store safely without crashing
        composeTestRule.onNodeWithTag("install_uber_button").performClick()
        composeTestRule.waitForIdle()

        // Dialog dismissed
        composeTestRule.onNodeWithTag("install_uber_button").assertDoesNotExist()

        val nextIntent = shadowOf(app).nextStartedActivity
        assertNotNull("Play store intent should be triggered", nextIntent)
        assertTrue(nextIntent.dataString?.contains(EmergencyTransportLauncher.UBER_PACKAGE) == true)
    }

    @Test
    fun testRapidoInstalledLaunchesDirectly() {
        val shadowPm = shadowOf(app.packageManager)
        val packageInfo = PackageInfo().apply {
            packageName = EmergencyTransportLauncher.RAPIDO_PACKAGE
            applicationInfo = ApplicationInfo().apply {
                packageName = EmergencyTransportLauncher.RAPIDO_PACKAGE
            }
        }
        shadowPm.installPackage(packageInfo)
        val resolveInfo = ResolveInfo().apply {
            activityInfo = ActivityInfo().apply {
                name = "com.rapido.passenger.MainActivity"
                packageName = EmergencyTransportLauncher.RAPIDO_PACKAGE
                applicationInfo = packageInfo.applicationInfo
            }
        }
        val launcherIntent = Intent(Intent.ACTION_MAIN)
            .addCategory(Intent.CATEGORY_LAUNCHER)
            .setPackage(EmergencyTransportLauncher.RAPIDO_PACKAGE)
        shadowPm.addResolveInfoForIntent(launcherIntent, resolveInfo)

        composeTestRule.setContent {
            EmergencyCenterScreen(
                viewModel = viewModel,
                onNavigateTo = {},
                onBack = {}
            )
        }

        composeTestRule.onNodeWithTag("emergency_center_screen")
            .performScrollToNode(hasTestTag("rapido_fallback_button"))
        composeTestRule.onNodeWithTag("rapido_fallback_button").performClick()
        composeTestRule.waitForIdle()

        // No not-installed dialog should appear
        composeTestRule.onNodeWithText("Rapido is not installed").assertDoesNotExist()

        val nextIntent = shadowOf(app).nextStartedActivity
        assertNotNull("Rapido activity intent should be started", nextIntent)
    }

    @Test
    fun testUberInstalledLaunchesDirectly() {
        val shadowPm = shadowOf(app.packageManager)
        val packageInfo = PackageInfo().apply {
            packageName = EmergencyTransportLauncher.UBER_PACKAGE
            applicationInfo = ApplicationInfo().apply {
                packageName = EmergencyTransportLauncher.UBER_PACKAGE
            }
        }
        shadowPm.installPackage(packageInfo)
        val resolveInfo = ResolveInfo().apply {
            activityInfo = ActivityInfo().apply {
                name = "com.ubercab.MainActivity"
                packageName = EmergencyTransportLauncher.UBER_PACKAGE
                applicationInfo = packageInfo.applicationInfo
            }
        }
        val launcherIntent = Intent(Intent.ACTION_MAIN)
            .addCategory(Intent.CATEGORY_LAUNCHER)
            .setPackage(EmergencyTransportLauncher.UBER_PACKAGE)
        shadowPm.addResolveInfoForIntent(launcherIntent, resolveInfo)

        composeTestRule.setContent {
            EmergencyCenterScreen(
                viewModel = viewModel,
                onNavigateTo = {},
                onBack = {}
            )
        }

        composeTestRule.onNodeWithTag("emergency_center_screen")
            .performScrollToNode(hasTestTag("uber_fallback_button"))
        composeTestRule.onNodeWithTag("uber_fallback_button").performClick()
        composeTestRule.waitForIdle()

        // No not-installed dialog should appear
        composeTestRule.onNodeWithText("Uber is not installed").assertDoesNotExist()

        val nextIntent = shadowOf(app).nextStartedActivity
        assertNotNull("Uber activity intent should be started", nextIntent)
    }

    @Test
    fun testNoLocationSafeLaunch() {
        var notInstalledCalled = false
        var errorCalled = false

        EmergencyTransportLauncher.launchRapido(
            context = app,
            latitude = null,
            longitude = null,
            onNotInstalled = { notInstalledCalled = true },
            onError = { errorCalled = true }
        )

        // Without installed app, onNotInstalled is cleanly called, zero crashes
        assertTrue(notInstalledCalled)
        assertFalse(errorCalled)

        notInstalledCalled = false
        errorCalled = false

        EmergencyTransportLauncher.launchUber(
            context = app,
            latitude = 0.0,
            longitude = 0.0,
            onNotInstalled = { notInstalledCalled = true },
            onError = { errorCalled = true }
        )

        assertTrue(notInstalledCalled)
        assertFalse(errorCalled)
    }
}
