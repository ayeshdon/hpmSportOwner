package com.happymesport.merchant.domain.repository

import android.app.Activity
import com.google.firebase.FirebaseException
import com.google.firebase.auth.PhoneAuthCredential
import com.happymesport.merchant.domain.model.UserModel

interface AuthRepository {
//    suspend fun loginWithGoogle(idToken: String): UserModel?
//
//    suspend fun loginWithFaceBook(idToken: String): UserModel?

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
