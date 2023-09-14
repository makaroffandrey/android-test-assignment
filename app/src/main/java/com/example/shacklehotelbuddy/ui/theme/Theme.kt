package com.example.shacklehotelbuddy.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.runtime.remember

object ShackleHotelBuddyTheme {
    val colors: CustomColors
        @Composable
        @ReadOnlyComposable
        get() = LocalColors.current

    val typography: CustomTypography
        @Composable
        @ReadOnlyComposable
        get() = LocalTypography.current
}

@Composable
fun ShackleHotelBuddyTheme(
    colors: CustomColors = ShackleHotelBuddyTheme.colors,
    typography: CustomTypography = ShackleHotelBuddyTheme.typography,
    isDarkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit,
) {
    val rememberedColors = remember { colors.copy() }.apply { updateColorsFrom(colors) }

    CompositionLocalProvider(
        LocalColors provides rememberedColors,
        LocalTypography provides typography
    ) {
        val colorScheme = if (isDarkTheme) {
            darkColorScheme(
                primary = rememberedColors.teal
            )
        } else {
            lightColorScheme(
                primary = rememberedColors.teal
            )
        }
        MaterialTheme(
            colorScheme = colorScheme
        ) {
            content()
        }
    }
}
