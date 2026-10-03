package com.example.ui.screens.emergency

import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri

/**
 * Safe, official app-launch handler for emergency fallback transport options (Rapido & Uber).
 * Gracefully handles installation checks, deep linking with location, app-store fallbacks,
 * and eliminates crash possibilities (ActivityNotFoundException, SecurityException, etc.).
 */
object EmergencyTransportLauncher {
    const val BLINKIT_PACKAGE = "com.grofers.customerapp"
    const val RAPIDO_PACKAGE = "com.rapido.passenger"
    const val UBER_PACKAGE = "com.ubercab"

    /**
     * Checks if a target application package is installed on the user's device.
     */
    fun isAppInstalled(context: Context, packageName: String): Boolean {
        return try {
            val pm = context.packageManager
            pm.getPackageInfo(packageName, 0)
            true
        } catch (_: PackageManager.NameNotFoundException) {
            false
        } catch (_: Exception) {
            false
        }
    }

     /**
     * Launches the Blinkit mobile application.
     * If not installed, invokes [onNotInstalled].
     */


    fun launchBlinkit(
    context: Context,
    onNotInstalled: () -> Unit,
    onError: (String) -> Unit
) {
    if (!isAppInstalled(context, BLINKIT_PACKAGE)) {
        onNotInstalled()
        return
    }

    try {
        val launchIntent =
            context.packageManager.getLaunchIntentForPackage(BLINKIT_PACKAGE)

        if (launchIntent != null) {
            launchIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            context.startActivity(launchIntent)
        } else {
            onNotInstalled()
        }
    } catch (_: Exception) {
        onError(
            "Unable to open Blinkit. Please install the app or try another transport option."
        )
    }
}





    /**
     * Launches the Rapido mobile application.
     * If not installed, invokes [onNotInstalled].
     */
     
    fun launchRapido(
        context: Context,
        latitude: Double? = null,
        longitude: Double? = null,
        onNotInstalled: () -> Unit,
        onError: (String) -> Unit
    ) {
        if (!isAppInstalled(context, RAPIDO_PACKAGE)) {
            onNotInstalled()
            return
        }

        try {
            val launchIntent = context.packageManager.getLaunchIntentForPackage(RAPIDO_PACKAGE)
            if (launchIntent != null) {
                launchIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                context.startActivity(launchIntent)
            } else {
                onNotInstalled()
            }
        } catch (_: Exception) {
            onError("Unable to open the ride app. Please install the app or try another transport option.")
        }
    }

    /**
     * Launches the Uber mobile application, safely passing current pickup location coordinates if available.
     * If not installed, invokes [onNotInstalled].
     */
    fun launchUber(
        context: Context,
        latitude: Double? = null,
        longitude: Double? = null,
        onNotInstalled: () -> Unit,
        onError: (String) -> Unit
    ) {
        if (!isAppInstalled(context, UBER_PACKAGE)) {
            onNotInstalled()
            return
        }

        try {
            // Uber officially supports deep linking with pickup coordinates
            val deepLinkUri = if (latitude != null && longitude != null && latitude != 0.0 && longitude != 0.0) {
                Uri.parse("uber://?action=setPickup&pickup[latitude]=$latitude&pickup[longitude]=$longitude")
            } else {
                Uri.parse("uber://?action=setPickup&pickup=my_location")
            }

            val deepLinkIntent = Intent(Intent.ACTION_VIEW, deepLinkUri).apply {
                setPackage(UBER_PACKAGE)
                addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            }

            if (deepLinkIntent.resolveActivity(context.packageManager) != null) {
                context.startActivity(deepLinkIntent)
            } else {
                val launchIntent = context.packageManager.getLaunchIntentForPackage(UBER_PACKAGE)
                if (launchIntent != null) {
                    launchIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                    context.startActivity(launchIntent)
                } else {
                    onNotInstalled()
                }
            }
        } catch (_: Exception) {
            try {
                val fallbackIntent = context.packageManager.getLaunchIntentForPackage(UBER_PACKAGE)
                if (fallbackIntent != null) {
                    fallbackIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                    context.startActivity(fallbackIntent)
                } else {
                    onError("Unable to open the ride app. Please install the app or try another transport option.")
                }
            } catch (_: Exception) {
                onError("Unable to open the ride app. Please install the app or try another transport option.")
            }
        }
    }

    /**
     * Opens the official Google Play Store listing for an uninstalled ride app.
     */
    fun openPlayStore(context: Context, packageName: String) {
        try {
            val marketIntent = Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=$packageName")).apply {
                addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            }
            context.startActivity(marketIntent)
        } catch (_: Exception) {
            try {
                val webIntent = Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=$packageName")).apply {
                    addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                }
                context.startActivity(webIntent)
            } catch (_: Exception) {
                // Failsafe: avoid crashes
            }
        }
    }
}
