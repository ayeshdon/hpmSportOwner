package com.happymesport.merchant.data.dto

import androidx.annotation.Keep
import com.google.firebase.Timestamp
import com.google.firebase.firestore.DocumentId

@Keep
data class UserDto(
    @DocumentId
    val uid: String = "",
    val mobileNumber: String = "",
    val createdAt: String? = "",
    val imageUrl: String? = "",
    val name: String? = "",
    val email: String? = "",
    val dateOfBirth: String? = "",
    val lastLoginTime: Timestamp? = null,
)
