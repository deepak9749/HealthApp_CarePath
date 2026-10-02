package com.example.ui.theme

import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext

private val DarkColorScheme =
  darkColorScheme(
    primary = HealthPrimaryDark,
    onPrimary = HealthOnPrimaryDark,
    primaryContainer = HealthPrimaryContainerDark,
    onPrimaryContainer = HealthOnPrimaryContainerDark,
    secondary = MedicalTealSecondary,
    secondaryContainer = MedicalTealSecondaryContainer,
    tertiary = MedicalTealTertiary,
    tertiaryContainer = MedicalTealTertiaryContainer,
    background = HealthSurfaceDark,
    onBackground = HealthOnSurfaceDark,
    surface = HealthSurfaceDark,
    onSurface = HealthOnSurfaceDark,
    surfaceVariant = HealthSurfaceVariantDark,
    onSurfaceVariant = HealthOnSurfaceDark,
    outline = HealthOutlineLight,
    error = EmergencyRedDark,
    errorContainer = EmergencyRed,
    onError = OnEmergencyRed
  )

private val LightColorScheme =
  lightColorScheme(
    primary = MedicalTealPrimary,
    onPrimary = MedicalTealOnPrimary,
    primaryContainer = MedicalTealContainer,
    onPrimaryContainer = MedicalTealOnContainer,
    secondary = MedicalTealSecondary,
    onSecondary = MedicalTealOnPrimary,
    secondaryContainer = MedicalTealSecondaryContainer,
    onSecondaryContainer = MedicalTealOnContainer,
    tertiary = MedicalTealTertiary,
    onTertiary = MedicalTealOnPrimary,
    tertiaryContainer = MedicalTealTertiaryContainer,
    onTertiaryContainer = MedicalTealOnContainer,
    background = HealthBackgroundLight,
    onBackground = HealthOnSurfaceLight,
    surface = HealthSurfaceLight,
    onSurface = HealthOnSurfaceLight,
    surfaceVariant = HealthSurfaceVariantLight,
    onSurfaceVariant = HealthOnSurfaceVariantLight,
    outline = HealthOutlineLight,
    outlineVariant = HealthOutlineVariantLight,
    error = EmergencyRed,
    errorContainer = EmergencyRedContainer,
    onError = OnEmergencyRed
  )

@Composable
fun SmartHealthTheme(
  darkTheme: Boolean = false,
  dynamicColor: Boolean = false,
  content: @Composable () -> Unit,
) {
  val colorScheme =
    when {
      dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
        val context = LocalContext.current
        if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
      }
      darkTheme -> DarkColorScheme
      else -> LightColorScheme
    }

  MaterialTheme(colorScheme = colorScheme, typography = Typography, content = content)
}

// Alias for backward compatibility
@Composable
fun MyApplicationTheme(
  darkTheme: Boolean = false,
  dynamicColor: Boolean = false,
  content: @Composable () -> Unit
) = SmartHealthTheme(darkTheme, dynamicColor, content)


