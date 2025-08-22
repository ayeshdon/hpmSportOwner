package com.happymesport.merchant.data.dto

import androidx.annotation.Keep

@Keep
data class OperationalHours(
    val open: String = "",
    val close: String = "",
    val ratePerHour: Double = 0.0,
)
