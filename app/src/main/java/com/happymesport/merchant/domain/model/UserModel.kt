package com.happymesport.merchant.domain.model

data class UserModel(
    val uuid: String,
    val name: String? = "",
    val email: String? = "",
    val mobileNumber: String,
    val createdAt: String?,
    val lastLoginTime: String?,
)
