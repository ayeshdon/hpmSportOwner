package com.happymesport.merchant.data.local.datastore

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import com.happymesport.merchant.utils.Constants
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class AuthTokenPrefDelegateImpl(
    private val context: Context,
) : AuthTokenPrefDelegate {
    override suspend fun saveAuthFlag(flag: Boolean) {
        context.dataStore.edit { settings ->
            settings[PreferenceKeys.TOKEN] = flag
        }
    }

    override suspend fun readAuthFlag(): Flow<Boolean> =
        context.dataStore.data
            .map { preferences ->
                preferences[PreferenceKeys.TOKEN] ?: false
            }
}

private val readOnlyProperty = preferencesDataStore(name = Constants.AUTH_SETTINGS)

val Context.dataStore: DataStore<Preferences> by readOnlyProperty

private object PreferenceKeys {
    val TOKEN = booleanPreferencesKey(Constants.AUTH_TOKEN)
}
