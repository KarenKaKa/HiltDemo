package com.syr.hiltdemo.base

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

/**
 * @author songyaru
 * @date 2020/8/17
 */
abstract class BaseViewModel : ViewModel() {
    /**
     * 运行在UI线程的协程 viewModelScope 已经实现了在onCleared取消协程
     */
    fun launchUI(block: suspend CoroutineScope.() -> Unit) = viewModelScope.launch {
        block()
    }
}