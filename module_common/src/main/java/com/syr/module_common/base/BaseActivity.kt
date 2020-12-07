package com.syr.module_common.base

import android.os.Bundle
import android.os.Handler
import android.os.Message
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.alibaba.android.arouter.launcher.ARouter
import java.lang.ref.WeakReference

/**
 * @author songyaru
 * @date 2020/8/17
 */
abstract class BaseActivity : AppCompatActivity() {
    inline fun <reified VM : ViewModel> viewModels(
        noinline factoryProducer: (() -> ViewModelProvider.Factory)? = null
    ): Lazy<VM> {
        val factoryPromise = factoryProducer ?: {
            defaultViewModelProviderFactory
        }
        return ViewModelLazy(VM::class, { viewModelStore }, factoryPromise)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        ARouter.getInstance().inject(this)
    }

    companion object {
         class MyHandler private constructor() : Handler() {
            private lateinit var activity: WeakReference<BaseActivity>
            private lateinit var handleMessage: (Message) -> Unit

            constructor(activity: BaseActivity, handleMessage: (Message) -> Unit) : this() {
                this.activity = WeakReference(activity)
                this.handleMessage = handleMessage
            }

            override fun handleMessage(msg: Message) {
                super.handleMessage(msg)
                activity.get()?.let {
                    if (it.isFinishing) return
                    this.handleMessage.invoke(msg)
                }
            }
        }
    }
}