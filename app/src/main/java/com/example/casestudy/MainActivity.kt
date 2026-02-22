package com.example.casestudy

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.*
import androidx.compose.ui.platform.LocalContext
import com.example.casestudy.ui.navigation.AppNavHost
import com.example.casestudy.ui.theme.CaseStudyTheme
import com.example.casestudy.util.SessionManager

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            val context = LocalContext.current
            val sessionManager = remember { SessionManager(context) }
            var isDarkMode by remember { mutableStateOf(sessionManager.isDarkMode()) }

            CaseStudyTheme(darkTheme = isDarkMode) {
                AppNavHost(
                    isDarkMode = isDarkMode,
                    onThemeChange = { enabled ->
                        isDarkMode = enabled
                        sessionManager.setDarkMode(enabled)
                    }
                )
            }
        }
    }
}
