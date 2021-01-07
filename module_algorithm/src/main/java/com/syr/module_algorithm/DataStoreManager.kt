package com.syr.module_algorithm

import android.app.Application
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.preferencesKey
import androidx.datastore.preferences.createDataStore
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import javax.inject.Inject

/**
 * Created by songyaru on 2020/12/29.
 */
object DataStoreManager {
//    @Inject
//    lateinit var mApplication: Application
//    lateinit var mDataStore: DataStore<Preferences>
//
//    suspend fun getName() {
//        mDataStore.data.map { it[preferencesKey("name")] }.first()
//    }
//
//    fun getTable(tableName: String): DataStore<Preferences> {
//        mDataStore = mApplication.createDataStore(name = tableName)
//        return mDataStore
//    }
}