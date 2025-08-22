package com.happymesport.merchant.data.dto

import androidx.annotation.Keep
import com.google.firebase.firestore.DocumentId

@Keep
data class FacilityDto(
    @DocumentId
    val uid: String = "",
    val name: String = "",
    val address: String = "",
    val location: LocationDto? = null,
    val phone: String? = "",
    val status: String? = "",
    val photos: List<String?>? = null,
    val sport: List<SportDto>? = null,
    val updatedAt: String? = "",
    val createdAt: String? = "",
)
