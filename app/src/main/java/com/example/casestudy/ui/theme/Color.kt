package com.example.casestudy.ui.theme

import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color

// Modern Dark Theme Palette (Cyan/Deep Dark)
val CyanPrimary = Color(0xFF00BCD4)
val CyanSecondary = Color(0xFF26C6DA)
val CyanTertiary = Color(0xFF80DEEA)

val DarkBackground = Color(0xFF0F0F0F)
val DarkSurface = Color(0xFF1A1A1A)
val DarkSurfaceVariant = Color(0xFF252525)

// Modern Light Theme Palette (Cyan/White)
val LightBackground = Color(0xFFFFFFFF)
val LightSurface = Color(0xFFF0F8FA)
val LightSurfaceVariant = Color(0xFFE0F7FA)

// Text Colors
val OnLightBackground = Color(0xFF00363D) // Dark Cyan/Teal instead of pure black
val OnLightSurface = Color(0xFF00363D)

val White = Color(0xFFFFFFFF)
val LightGray = Color(0xFFAAAAAA)
val ErrorRed = Color(0xFFE53935)
val SuccessGreen = Color(0xFF4CAF50)

// Gradients
val PrimaryGradient = Brush.verticalGradient(
    colors = listOf(DarkBackground, DarkSurface, Color(0xFF121212))
)

val LightPrimaryGradient = Brush.verticalGradient(
    colors = listOf(LightBackground, LightSurface, Color(0xFFF0F0F0))
)

val CyanGradient = Brush.linearGradient(
    colors = listOf(CyanPrimary, CyanSecondary)
)

val LoginBackgroundGradient = Brush.verticalGradient(
    colors = listOf(
        Color(0xFF081214),
        Color(0xFF0F2027),
        Color(0xFF203A43)
    )
)
