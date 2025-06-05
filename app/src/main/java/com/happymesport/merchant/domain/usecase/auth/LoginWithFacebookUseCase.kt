package com.happymesport.merchant.domain.usecase.auth

import com.happymesport.merchant.common.Resources
import com.happymesport.merchant.domain.model.UserModel
import com.happymesport.merchant.domain.repository.AuthRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class LoginWithFacebookUseCase
    @Inject
    constructor(
        private val repository: AuthRepository,
    ) {
        operator fun invoke(idToken: String): Flow<Resources<UserModel>> =
            flow {
                try {
                    emit(Resources.Loading())
//                    val response = repository.loginWithFaceBook(idToken)
//                    emit(Resources.Success(response))
                } catch (e: Exception) {
                    emit(Resources.Error(e.message.toString()))
                }
            }
    }
