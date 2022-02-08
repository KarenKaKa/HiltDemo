package com.syr.module_algorithm

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map

/**
 * Created by songyaru on 2020/12/29.
 */
object DataStoreManager {
    private val Context.mDataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")
    private val KEY_NAME = stringPreferencesKey("name")

    suspend fun getName(context: Context): String? {
        return context.mDataStore.data.map { it[KEY_NAME] }.first()
    }

    suspend fun setName(context: Context, name: String) {
        context.mDataStore.edit {
            it[KEY_NAME] = name
        }
    }
}