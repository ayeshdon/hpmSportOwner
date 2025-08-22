package com.happymesport.merchant.domain.usecase.user

import com.happymesport.merchant.data.local.datastore.UserDataPrefDelegate
import com.happymesport.merchant.domain.model.UserModel
import kotlinx.coroutines.flow.Flow

class GetUserProfileDataUseCase(
    private val pref: UserDataPrefDelegate,
) {
    suspend operator fun invoke(): Flow<UserModel?> = pref.getProfileData()
}
