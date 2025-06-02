package com.happymesport.merchant.domain.usecase.auth

data class AuthTokenUseCase(
    val readAuthToken: ReadAuthTokenUseCase,
    val saveAuthToken: SaveAuthTokenUseCase
)