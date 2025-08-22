package com.happymesport.merchant.domain.usecase.user

import com.happymesport.merchant.data.local.datastore.UserDataPrefDelegate
import com.happymesport.merchant.domain.model.UserModel

class SaveProfileDataUseCase(
    private val pref: UserDataPrefDelegate,
) {
    suspend operator fun invoke(model: UserModel?) = pref.saveProfileData(model)
}
