package com.happymesport.merchant.domain.usecase.auth

data class ProfileCompleteUseCase(
    val readProfileCompleteUseCase: ReadProfileCompleteUseCase,
    val saveProfileCompleteUseCase: SaveProfileCompleteUseCase,
)
