package com.example.casestudy.ui.theme

import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color

// Modern Dark Theme Palette (Cyan/Deep Dark)
val CyanPrimary = Color(0xFF00BCD4)
val CyanSecondary = Color(0xFF26C6DA)
val CyanTertiary = Color(0xFF80DEEA)

val Purple40 = Color(0xFF6650a4)
val PurpleGrey40 = Color(0xFF625b71)
val Pink40 = Color(0xFF7D5260)

// Soft Cartoonish Colors
val PastelBlue = Color(0xFFAED9E0)
val PastelPink = Color(0xFFFAF3DD)
val PastelYellow = Color(0xFFFFF4E0)
val SoftPeach = Color(0xFFFFDAB9)
val MintGreen = Color(0xFFB8F2E6)
val BubblegumPink = Color(0xFFF19FB2)
val SoftLavender = Color(0xFFE8E1EF)
val DarkText = Color(0xFF2D3142)

// Missing colors for Theme.kt
val DarkBackground = Color(0xFF121212)
val DarkSurface = Color(0xFF1E1E1E)
val DarkSurfaceVariant = Color(0xFF2C2C2C)

val LightBackground = Color(0xFFF5F5F5)
val LightSurface = Color(0xFFFFFFFF)
val LightSurfaceVariant = Color(0xFFE0E0E0)
val OnLightBackground = Color(0xFF212121)
val OnLightSurface = Color(0xFF212121)

// Gradient for backgrounds
val LoginBackgroundGradient = Brush.verticalGradient(
    colors = listOf(CyanPrimary, CyanSecondary)
)
