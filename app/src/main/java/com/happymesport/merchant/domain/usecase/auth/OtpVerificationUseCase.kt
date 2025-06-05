package com.happymesport.merchant.domain.usecase.auth

import com.happymesport.merchant.common.Resources
import com.happymesport.merchant.domain.repository.AuthRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import timber.log.Timber
import javax.inject.Inject

class OtpVerificationUseCase
    @Inject
    constructor(
        private val repository: AuthRepository,
    ) {
        operator fun invoke(
            verificationId: String,
            code: String,
            onSuccess: () -> Unit,
            onError: (String) -> Unit,
        ): Flow<Resources<Any>> =
            flow {
                try {
                    Timber.e(" UC code: $code")
                    emit(Resources.Loading())
                    repository.otpVerificationWithFirebase(
                        verificationId,
                        code,
                        onSuccess,
                        onError,
                    )
                    emit(Resources.Success(null))
                } catch (e: Exception) {
                    emit(Resources.Error(e.message.toString()))
                }
            }
    }
