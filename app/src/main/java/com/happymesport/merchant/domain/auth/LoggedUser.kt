package com.happymesport.merchant.domain.auth

data class LoggedUser(
    val uuid: String,
    val name: String,
    val url: String,
    val email: String,
    val phone: String,
)
