package com.happymesport.merchant.data.dto

import androidx.annotation.Keep

@Keep
data class LocationDto (
    val lat: Double = 0.0,
    val lon: Double = 0.0
)