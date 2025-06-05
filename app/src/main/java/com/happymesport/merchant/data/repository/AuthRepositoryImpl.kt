package com.happymesport.merchant.data.repository

import android.app.Activity
import com.google.firebase.FirebaseException
import com.google.firebase.auth.PhoneAuthCredential
import com.happymesport.merchant.data.auth.FirebaseAuthDataSource
import com.happymesport.merchant.domain.model.UserModel
import com.happymesport.merchant.domain.repository.AuthRepository
import timber.log.Timber
import javax.inject.Inject

class AuthRepositoryImpl
    @Inject
    constructor(
        private val authDataSource: FirebaseAuthDataSource,
    ) : AuthRepository {
//        override suspend fun loginWithGoogle(idToken: String): UserModel? = authDataSource.signInWithGoogle(idToken)
//
//        override suspend fun loginWithFaceBook(idToken: String): UserModel? = authDataSource.signInWithFacebook(idToken)

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
            Timber.e(" REPO code: $code")
            authDataSource.verifyOtpCode(verificationId, code, onSuccess, onError)
        }
    }
