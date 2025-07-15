package com.happymesport.merchant.presantation.theme

import androidx.compose.material3.ColorScheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.luminance

val AppThemePrimary = Color(0xFF00796B)
val AppThemeSecondary = Color(0xFF039589)
val AppThemeLight = Color(0xFF4DB6AC)
val ErrorRed = Color(0xFFD32F2F)
val White = Color(0xFFFFFFFF)
val BgGrey = Color(0xFFececec)

// app color
val textDark = Color(0xFF000000)
val textLight = Color(0xFF455A64)

val ColorScheme.lightText: Color
    @Composable
    get() =
        if (MaterialTheme.colorScheme.isLight()) {
            textLight
        } else {
            textLight
        }
val ColorScheme.white: Color
    @Composable
    get() =
        if (MaterialTheme.colorScheme.isLight()) {
            White
        } else {
            White
        }

val ColorScheme.background: Color
    @Composable
    get() =
        if (MaterialTheme.colorScheme.isLight()) {
            BgGrey
        } else {
            BgGrey
        }

@Composable
private fun ColorScheme.isLight(): Boolean = this.background.luminance() > 0.5f
