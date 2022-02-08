package com.syr.hiltdemo.module.main

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.lifecycleScope
import com.syr.hiltdemo.R
import com.syr.hiltdemo.databinding.ActivityMainBinding
import com.syr.hiltdemo.module.ContainerActivity
import com.syr.hiltdemo.module.details.SourceFrom
import com.syr.hiltdemo.module.home.HomeActivity
import com.syr.hiltdemo.utils.showToast
import com.syr.module_common.base.BaseActivity
import com.syr.module_common.net.core.RouterHub
import com.syr.module_common.net.core.Utils
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import kotlin.concurrent.thread

@AndroidEntryPoint
class MainActivity : BaseActivity() {
    private val TAG = MainActivity::class.java.simpleName
    private val viewModel by viewModels<MainViewModel>()
    private lateinit var binding: ActivityMainBinding

    private lateinit var mHandler: Handler
    private lateinit var threadHandler: Handler

    private val countDownTimer = flow {
        for (i in 5 downTo 0) {
            emit(i)
            delay(1000)
        }
    }.flowOn(Dispatchers.Main)
        .onStart {
            showToast("自定义的toast开始啦")
        }
        .onCompletion {
            showToast("自定义的toast结束啦")
        }
        .onEach {
            Log.e(TAG, "倒计时${it}s")
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        countDownTimer.launchIn(lifecycleScope)

//        Utils.navigation(RouterHub.ALGORITHM_ALGORITHMACTIVITY, this)
//        finish()

        binding = DataBindingUtil.setContentView(
            this, R.layout.activity_main
        )
        binding.viewModel = viewModel
        viewModel.toHome.observe(this, {
            startActivity(Intent(this, HomeActivity::class.java))
        })

        binding.tvAlgorithm.setOnClickListener {
            Utils.navigation(RouterHub.ALGORITHM_ALGORITHMACTIVITY, this)
        }
        mHandler = Companion.MyHandler(this) {
            when (it.what) {
                0 -> {
                    Log.e(TAG, "0 接收UI线程发送的消息")
                }
                else -> {
                    Log.e(TAG, "else 接收子线程的发送消息")
                }
            }
        }
        mHandler.sendEmptyMessage(0)
        Log.e(TAG, "0 Thread.name=${Thread.currentThread().name}")
        thread {
            Log.e(TAG, "1 Thread.name=${Thread.currentThread().name}")
            mHandler.sendMessage(mHandler.obtainMessage(111))
            Looper.prepare()
            threadHandler = Companion.MyHandler(this) {
                when (it.what) {
                    0 -> {
                        Log.e(TAG, "子线程 0 接收子线程发送的消息")
                    }
                    1 -> {
                        Log.e(TAG, "子线程 1 接收子线程发送的消息")
                    }
                    else -> {
                        Log.e(TAG, "子线程 else 接收主线程发送的消息")
                    }
                }
            }
            threadHandler.sendEmptyMessage(0)
            Looper.loop()
        }

        GlobalScope.launch {
            delay(1000)
            Log.e(TAG, "2 Thread.name=${Thread.currentThread().name}")
            threadHandler.sendEmptyMessage(1)
        }
        binding.tvHandler.setOnClickListener {
            Log.e(TAG, "3 Thread.name=${Thread.currentThread().name}")
            threadHandler.sendEmptyMessage(111)
        }

        binding.toArticlesFragment.setOnClickListener {
            ContainerActivity.launchActivity(this, SourceFrom.ARTICLES)
        }
    }
}
