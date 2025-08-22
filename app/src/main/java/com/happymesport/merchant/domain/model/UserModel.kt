package com.happymesport.merchant.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class UserModel(
    val uuid: String,
    val name: String? = null,
    val dob: String? = null,
    val email: String? = null,
    val mobileNumber: String,
    val imageUrl: String?,
    val createdAt: String?,
    val lastLoginTime: String?,
)
