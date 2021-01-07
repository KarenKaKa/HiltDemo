package com.syr.module_algorithm

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.preferencesKey
import androidx.datastore.preferences.createDataStore
import com.alibaba.android.arouter.facade.annotation.Route
import com.syr.module_common.net.core.RouterHub
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.runBlocking

@Route(path = RouterHub.ALGORITHM_ALGORITHMACTIVITY)
class AlgorithmActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_algorithm)

        val dataStore: DataStore<Preferences> = createDataStore(name = "settings")
        runBlocking {
            dataStore.edit {
                it[preferencesKey<String>("name")] = "john"
            }

            val name = dataStore.data.map { it[preferencesKey<String>("name")] }.first()
            Log.e("name", "name=${name}")
        }


    }

}