package com.happymesport.merchant.data.dto

import androidx.annotation.Keep
import com.google.firebase.firestore.DocumentId
import kotlinx.serialization.Serializable

@Keep
data class AvailableFacility(
    @DocumentId
    val uid: String = "",
    val name: String? = "",
    val description: String? = "",
    val icon: String? = "",
)
