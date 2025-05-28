package com.happymesport.merchant.domain.auth

import android.app.Activity
import com.google.firebase.FirebaseException
import com.google.firebase.auth.PhoneAuthCredential

interface AuthRepository {
    suspend fun loginWithGoogle(idToken: String): LoggedUser?

    suspend fun loginWithFaceBook(idToken: String): LoggedUser?

    suspend fun loginWithMobile(
        phoneNumber: String,
        onVerificationCompleted: (PhoneAuthCredential) -> Unit,
        onVerificationFailed: (FirebaseException) -> Unit,
        onCodeSent: (verificationId: String) -> Unit,
        activity: Activity
    )
}
