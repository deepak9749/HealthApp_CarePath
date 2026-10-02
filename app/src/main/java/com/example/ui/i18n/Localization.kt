package com.example.ui.i18n

import androidx.compose.runtime.Composable
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.runtime.staticCompositionLocalOf

/**
 * CompositionLocal providing the currently active app language code ("EN", "HI", "BN").
 */
val LocalAppLanguage = staticCompositionLocalOf { "EN" }

/**
 * Translate a static or dynamic UI string based on the current composition language.
 */
@Composable
@ReadOnlyComposable
fun tr(key: String): String {
    val lang = LocalAppLanguage.current
    return AppTranslations.translate(key, lang)
}

/**
 * Translate a formatted string with arguments based on the current composition language.
 */
@Composable
@ReadOnlyComposable
fun tr(format: String, vararg args: Any): String {
    val lang = LocalAppLanguage.current
    return AppTranslations.translateFormat(format, lang, *args)
}

/**
 * Non-composable helper to translate a string for a specific language code.
 */
fun trText(key: String, lang: String): String {
    return AppTranslations.translate(key, lang)
}

/**
 * Non-composable helper to translate a formatted string for a specific language code.
 */
fun trFormat(format: String, lang: String, vararg args: Any): String {
    return AppTranslations.translateFormat(format, lang, *args)
}
