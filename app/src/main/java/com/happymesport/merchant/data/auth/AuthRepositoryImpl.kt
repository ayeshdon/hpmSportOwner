package com.happymesport.merchant.data.auth

import android.app.Activity
import com.google.firebase.FirebaseException
import com.google.firebase.auth.PhoneAuthCredential
import com.happymesport.merchant.domain.model.LoggedUser
import com.happymesport.merchant.domain.repository.AuthRepository
import javax.inject.Inject

class AuthRepositoryImpl
    @Inject
    constructor(
        private val authDataSource: FirebaseAuthDataSource,
    ) : AuthRepository {
        override suspend fun loginWithGoogle(idToken: String): LoggedUser? = authDataSource.signInWithGoogle(idToken)

        override suspend fun loginWithFaceBook(idToken: String): LoggedUser? = authDataSource.signInWithFacebook(idToken)

        override suspend fun loginWithMobile(
            phoneNumber: String,
            onVerificationCompleted: (PhoneAuthCredential) -> Unit,
            onVerificationFailed: (FirebaseException) -> Unit,
            onCodeSent: (verificationId: String) -> Unit,
            activity: Activity,
        ) {
            authDataSource.loginWithMobileNumber(
                phoneNumber,
                onVerificationCompleted,
                onVerificationFailed,
                onCodeSent,
                activity,
            )
        }

        override suspend fun otpVerificationWithFirebase(
            verificationId: String,
            code: String,
            onSuccess: () -> Unit,
            onError: (String) -> Unit,
        ) {
            authDataSource.verifyOtpCode(verificationId, code, onSuccess, onError)
        }
    }
