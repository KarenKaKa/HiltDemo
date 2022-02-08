package com.syr.module_algorithm

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.alibaba.android.arouter.facade.annotation.Route
import com.syr.module_common.net.core.RouterHub
import kotlinx.coroutines.runBlocking

@Route(path = RouterHub.ALGORITHM_ALGORITHMACTIVITY)
class AlgorithmActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_algorithm)

        runBlocking {
            DataStoreManager.setName(this@AlgorithmActivity, "John")
            Log.e("name", "name=${DataStoreManager.getName(this@AlgorithmActivity)}")
        }
    }
}