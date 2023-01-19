package com.example.bhoomicam.view.utils

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.*
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.first


const val DATASTORE_NAME = "BhoomiCam"
val Context.datastore: DataStore<androidx.datastore.preferences.core.Preferences> by preferencesDataStore(DATASTORE_NAME)

class Datastore(val context: Context) {

    companion object {
        const val LOGIN_KEY = "LOGIN_KEY"

    }

    suspend fun saveUserDetails(key: String, value: String) {
        val key1 = stringPreferencesKey(key)
        context.datastore.edit {
            it[key1] = value
        }
    }
    suspend fun changeLoginState(value: Boolean) {
        val key1 = booleanPreferencesKey(LOGIN_KEY)
        context.datastore.edit {
            it[key1] = value
        }
    }
    suspend fun getUserDetails(key: String): String? {
        val key1 = stringPreferencesKey(key)
        return context.datastore.data.first()[key1]
    }

    suspend fun isLogin(): Boolean {
        val key1 = booleanPreferencesKey(LOGIN_KEY)
        return context.datastore.data.first()[key1] ?: false
    }
}