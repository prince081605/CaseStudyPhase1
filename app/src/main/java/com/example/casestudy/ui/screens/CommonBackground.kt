package com.example.casestudy.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.casestudy.ui.theme.LoginBackgroundGradient

@Composable
fun AppBackground(content: @Composable () -> Unit) {
    val isDarkMode = MaterialTheme.colorScheme.background.red < 0.5f
    
    Box(
        modifier = Modifier
            .fillMaxSize()
            .then(
                if (isDarkMode) {
                    Modifier.background(LoginBackgroundGradient)
                } else {
                    Modifier.background(MaterialTheme.colorScheme.background)
                }
            )
    ) {
        content()
    }
}
