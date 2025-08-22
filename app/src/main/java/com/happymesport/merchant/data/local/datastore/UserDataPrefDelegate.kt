package com.happymesport.merchant.data.local.datastore

import com.happymesport.merchant.domain.model.UserModel
import kotlinx.coroutines.flow.Flow

interface UserDataPrefDelegate {
    suspend fun saveProfileData(userModel: UserModel?)

    suspend fun getProfileData(): Flow<UserModel?>
}
