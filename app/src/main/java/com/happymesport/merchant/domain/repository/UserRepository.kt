package com.happymesport.merchant.domain.repository

import com.google.firebase.Timestamp
import com.happymesport.merchant.common.Resources
import com.happymesport.merchant.domain.model.UserModel

interface UserRepository {
    suspend fun getUser(uid: String): Resources<UserModel?>

    suspend fun addUser(user: UserModel): Resources<Unit>

    suspend fun updateLastLoginTime(
        uid: String,
        timestamp: Timestamp,
    ): Resources<Unit>
}
