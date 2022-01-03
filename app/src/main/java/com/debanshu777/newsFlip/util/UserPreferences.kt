package com.debanshu777.newsFlip.util

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map

private const val USER_PREFERENCES_NAME = "settings"
private val Context.dataStore by preferencesDataStore(USER_PREFERENCES_NAME)

object UserPreferences {
    suspend fun setValue(context: Context, key: String, value: String) {
        val wrapperKey = stringPreferencesKey(key)
        context.dataStore.edit {
            it[wrapperKey] = value
        }
    }

    suspend fun getValue(context: Context, key: String, default: String): String {
        val wrapperKey = stringPreferencesKey(key)
        val flowVal: Flow<String> = context.dataStore.data.map {
            it[wrapperKey] ?: default
        }
        return flowVal.first()
    }
}
