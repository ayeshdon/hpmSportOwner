package com.happymesport.merchant.presantation.theme

import androidx.compose.runtime.compositionLocalOf
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

data class Spacing(
    val small: Dp = 5.dp,
    val medium: Dp = 10.dp,
    val default: Dp = 20.dp,
    val large: Dp = 30.dp,
    val cornerRadiusS: Dp = 5.dp,
    val cornerRadius: Dp = 10.dp,
    val cornerRadiusL: Dp = 20.dp,
    val lineHeight: Dp = 1.dp,
    val imageBorder: Dp = 2.dp,
    val appBarHeight: Dp = 100.dp,
)

val LocalSpacing = compositionLocalOf { Spacing() }
