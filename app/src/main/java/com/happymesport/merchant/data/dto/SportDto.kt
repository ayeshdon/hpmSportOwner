package com.happymesport.merchant.data.dto

import androidx.annotation.Keep

@Keep
data class SportDto(
    val uid: String = "",
    val name: String = "",
    val iconUrl: String = "",
    val operatingHours: OperationalHours? = null,
)
