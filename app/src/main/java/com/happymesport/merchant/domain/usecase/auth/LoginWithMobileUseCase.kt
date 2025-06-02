package com.happymesport.merchant.domain.usecase.auth

import android.app.Activity
import com.google.firebase.FirebaseException
import com.google.firebase.auth.PhoneAuthCredential
import com.happymesport.merchant.common.Resources
import com.happymesport.merchant.domain.repository.AuthRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import timber.log.Timber
import javax.inject.Inject

class LoginWithMobileUseCase
    @Inject
    constructor(
        private val repository: AuthRepository,
    ) {
        operator fun invoke(
            phoneNumber: String,
            onVerificationCompleted: (PhoneAuthCredential) -> Unit,
            onVerificationFailed: (FirebaseException) -> Unit,
            onCodeSent: (verificationId: String) -> Unit,
            activity: Activity
        ): Flow<Resources<Any>> =
            flow {
                try {
                    Timber.e("LOGIN  01 ")
                    emit(Resources.Loading())
                    Timber.e("LOGIN  02 ")
                    repository.loginWithMobile(
                        phoneNumber,
                        onVerificationCompleted,
                        onVerificationFailed,
                        onCodeSent,
                        activity
                    )
                    emit(Resources.Success(null))
                } catch (e: Exception) {
                    emit(Resources.Error(e.message.toString()))
                }
            }
    }
