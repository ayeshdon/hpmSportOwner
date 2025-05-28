package com.happymesport.merchant.presantation.navigation.screens

import kotlinx.serialization.Serializable

@Serializable
object Onboarding

@Serializable
object SignInScreen

@Serializable
class SignInOtpScreen(
    val mobileNumber: String,
    val code: String,
)
