package com.example.ui.i18n

enum class AppLanguage(val code: String, val displayName: String, val nativeName: String) {
    EN("EN", "English", "English (Global)"),
    HI("HI", "Hindi", "हिंदी (Hindi)"),
    BN("BN", "Bengali", "বাংলা (Bengali)");

    companion object {
        fun fromCode(code: String): AppLanguage =
            entries.firstOrNull { it.code.equals(code, ignoreCase = true) } ?: EN
    }
}
