package com.happymesport.merchant.domain.repository

import android.app.Activity
import com.google.firebase.FirebaseException
import com.google.firebase.auth.PhoneAuthCredential
import com.happymesport.merchant.domain.model.LoggedUser

interface AuthRepository {
    suspend fun loginWithGoogle(idToken: String): LoggedUser?

    suspend fun loginWithFaceBook(idToken: String): LoggedUser?

    suspend fun loginWithMobile(
        phoneNumber: String,
        onVerificationCompleted: (PhoneAuthCredential) -> Unit,
        onVerificationFailed: (FirebaseException) -> Unit,
        onCodeSent: (verificationId: String) -> Unit,
        activity: Activity,
    )

    suspend fun otpVerificationWithFirebase(
        verificationId: String,
        code: String,
        onSuccess: () -> Unit,
        onError: (String) -> Unit,
    )
}
