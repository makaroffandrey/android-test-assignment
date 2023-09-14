package com.example.shacklehotelbuddy.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

internal val bodyMedium: TextStyle = TextStyle(
    fontFamily = FontFamily.Default,
    fontWeight = FontWeight.W400,
    fontSize = 14.sp,
    lineHeight = 20.sp,
    letterSpacing = 0.5.sp
)

internal val displayMedium = TextStyle(
    fontFamily = FontFamily.Default,
    fontWeight = FontWeight(450),
    fontSize = 44.sp,
    lineHeight = 48.sp,
)

internal val titleLarge = TextStyle(
    fontFamily = FontFamily.Default,
    fontWeight = FontWeight(450),
    fontSize = 20.sp,
    lineHeight = 22.sp,
)
internal val Typography = Typography(
    bodyMedium = bodyMedium,
    displayMedium = displayMedium,
    titleLarge = titleLarge,
)
