package com.example.shacklehotelbuddy.theme

import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.ui.graphics.Color

internal val Black = Color(0xFF000000)
internal val White = Color(0xFFFFFFFF)
internal val Teal = Color(0xFF2CABB1)
internal val GrayBorder = Color(0xFFDDDDDD)
internal val GrayText = Color(0xFF6D6D6D)


internal val LightColorScheme = lightColorScheme(
    primary = Teal,
    surface = Color.White,
    onSurface = GrayText,
    onSurfaceVariant = Color.Black,
)

internal val DarkColorScheme = darkColorScheme(
    primary = Teal
)
