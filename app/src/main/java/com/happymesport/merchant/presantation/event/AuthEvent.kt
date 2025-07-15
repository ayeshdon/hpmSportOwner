package com.happymesport.merchant.presantation.event

import android.app.Activity

sealed class AuthEvent : BaseEvent {
    data class LoginWithGoogle(
        val idToken: String,
    ) : AuthEvent()

    data class LoginWithFacebook(
        val idToken: String,
    ) : AuthEvent()

    data class LoginWithMobile(
        val phone: String,
        val activity: Activity,
    ) : AuthEvent()

    data class LoginWithMobileOtpVerify(
        val phone: String,
        val otp: String,
        val verificationId: String,
    ) : AuthEvent()

    data object LoginWithMobileReset : AuthEvent()

    data object Logout : AuthEvent()

    data object GetAuthFlag : AuthEvent()

}
