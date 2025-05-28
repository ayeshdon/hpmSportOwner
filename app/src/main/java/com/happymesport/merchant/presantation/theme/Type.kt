package com.happymesport.merchant.presantation.theme

import androidx.compose.material3.Typography
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

val fontFamily = FontFamily.SansSerif

fun appTypography() =
    Typography(
        bodyLarge =
            TextStyle(
                fontFamily = fontFamily,
                fontWeight = FontWeight.Normal,
                fontSize = 16.sp,
                lineHeight = 24.sp,
                letterSpacing = 0.5.sp,
            ),
        titleLarge =
            TextStyle(
                fontFamily = fontFamily,
                fontWeight = FontWeight.Normal,
                fontSize = 22.sp,
                lineHeight = 28.sp,
                letterSpacing = 0.sp,
            ),
        labelSmall =
            TextStyle(
                fontFamily = fontFamily,
                fontWeight = FontWeight.Medium,
                fontSize = 11.sp,
                lineHeight = 16.sp,
                letterSpacing = 0.5.sp,
            ),
    )

val Typography.header: TextStyle
    get() =
        TextStyle(
            fontSize = 24.sp,
            fontFamily = fontFamily,
            fontWeight = FontWeight.Bold,
        )

val Typography.mainHeader: TextStyle
    get() =
        TextStyle(
            fontFamily = fontFamily,
            fontSize = 30.sp,
            fontWeight = FontWeight.SemiBold,
        )

val LocalTypography = compositionLocalOf { appTypography() }
