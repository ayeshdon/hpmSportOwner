package com.happymesport.merchant.domain.usecase.auth

import com.happymesport.merchant.domain.usecase.user.SaveProfileDataUseCase
import javax.inject.Inject

class LogoutUseCase
    @Inject
    constructor(
        private val saveProfileCompleteUseCase: SaveProfileCompleteUseCase,
        private val saveUserProfileUseCase: SaveProfileDataUseCase,
        private val saveAuthTokenUseCase: SaveAuthTokenUseCase,
    ) {
        suspend operator fun invoke() {
            saveProfileCompleteUseCase.invoke(false)
            saveAuthTokenUseCase.invoke(false)
            saveUserProfileUseCase(null)
        }
    }
