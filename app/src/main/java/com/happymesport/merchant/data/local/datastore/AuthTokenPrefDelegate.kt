package com.happymesport.merchant.data.local.datastore

import kotlinx.coroutines.flow.Flow

interface AuthTokenPrefDelegate {
    suspend fun saveAuthFlag(flag: Boolean)

    suspend fun readAuthFlag(): Flow<Boolean>

    suspend fun readUserProfileCompleteFlag(): Flow<Boolean>

    suspend fun saveUserProfileCompleteFlag(flag: Boolean)
}
