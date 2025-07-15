package com.happymesport.merchant.data.dto

import com.google.firebase.firestore.DocumentId

data class UserDto(
    @DocumentId
    val uid: String = "",
    val mobileNumber: String = "",
    val createdAt: String? = "",
    val imageUrl: String? = "",
    val lastLoginTime: String? = "",
)
