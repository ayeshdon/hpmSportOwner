package com.happymesport.merchant.domain.usecase.auth

import com.happymesport.merchant.data.local.datastore.AuthTokenPrefDelegate
import kotlinx.coroutines.flow.Flow

class ReadProfileCompleteUseCase(
    private val tokenManger: AuthTokenPrefDelegate,
) {
    suspend operator fun invoke(): Flow<Boolean> = tokenManger.readUserProfileCompleteFlag()
}
