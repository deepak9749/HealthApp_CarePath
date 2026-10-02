package com.example

import android.content.res.Configuration
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.LocalActivityResultRegistryOwner
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.ui.SmartHealthViewModel
import com.example.ui.i18n.LocalAppLanguage
import com.example.ui.navigation.SmartHealthNavHost
import com.example.ui.theme.SmartHealthTheme
import java.util.Locale

class MainActivity : ComponentActivity() {

    private val viewModel: SmartHealthViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val currentLanguage by viewModel.currentLanguage.collectAsStateWithLifecycle()

            val locale = remember(currentLanguage) {
                when (currentLanguage) {
                    "HI" -> Locale("hi", "IN")
                    "BN" -> Locale("bn", "IN")
                    else -> Locale("en", "US")
                }
            }

            val currentConfig = LocalConfiguration.current
            val localizedConfig = remember(locale, currentConfig) {
                Configuration(currentConfig).apply {
                    setLocale(locale)
                }
            }

            val context = LocalContext.current
            val localizedContext = remember(locale, context) {
                context.createConfigurationContext(localizedConfig)
            }

            CompositionLocalProvider(
                LocalAppLanguage provides currentLanguage,
                LocalConfiguration provides localizedConfig,
                LocalContext provides localizedContext,
                LocalActivityResultRegistryOwner provides this
            ) {
                SmartHealthTheme {
                    Surface(modifier = Modifier.fillMaxSize()) {
                        SmartHealthNavHost(viewModel = viewModel)
                    }
                }
            }
        }
    }
}

