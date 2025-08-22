package com.happymesport.merchant.data.local.datastore

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.happymesport.merchant.domain.model.UserModel
import com.happymesport.merchant.utils.Constants
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.serialization.json.Json

class UserDataPrefDelegateImpl(
    private val context: Context,
) : UserDataPrefDelegate {
    override suspend fun getProfileData(): Flow<UserModel?> =
        context.userDataStore.data
            .map { preferences ->
                val userJson = preferences[UserPreferenceKeys.USER_PROFILE_DATA]
                userJson?.let {
                    try {
                        Json.decodeFromString<UserModel>(it)
                    } catch (e: Exception) {
                        e.printStackTrace()
                        null
                    }
                }
            }

    override suspend fun saveProfileData(userModel: UserModel?) {
        context.userDataStore.edit { preferences ->
            val userJson = Json.encodeToString(userModel)
            preferences[UserPreferenceKeys.USER_PROFILE_DATA] = userJson
        }
    }
}

private val readOnlyProperty = preferencesDataStore(name = Constants.USER_SETTINGS)

val Context.userDataStore: DataStore<Preferences> by readOnlyProperty

private object UserPreferenceKeys {
    val USER_PROFILE_DATA = stringPreferencesKey(Constants.USER_PROFILE_DATA)
}
