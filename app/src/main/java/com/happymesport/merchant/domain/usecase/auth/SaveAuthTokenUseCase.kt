package com.happymesport.merchant.domain.usecase.auth

import com.happymesport.merchant.data.local.datastore.AuthTokenPrefDelegate

class SaveAuthTokenUseCase(
    private val tokenManger: AuthTokenPrefDelegate,
) {
    suspend operator fun invoke(flag: Boolean) = tokenManger.saveAuthFlag(flag)
}
