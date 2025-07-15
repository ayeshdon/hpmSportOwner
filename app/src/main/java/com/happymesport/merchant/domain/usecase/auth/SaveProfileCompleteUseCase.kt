package com.happymesport.merchant.domain.usecase.auth

import com.happymesport.merchant.data.local.datastore.AuthTokenPrefDelegate

class SaveProfileCompleteUseCase(
    private val tokenManger: AuthTokenPrefDelegate,
) {
    suspend operator fun invoke(flag: Boolean) = tokenManger.saveUserProfileCompleteFlag(flag)
}
