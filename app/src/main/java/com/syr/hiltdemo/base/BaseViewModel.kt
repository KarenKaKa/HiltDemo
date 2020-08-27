package com.syr.hiltdemo.base

import androidx.lifecycle.ViewModel
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.internal.disposables.DisposableContainer

/**
 * @author songyaru
 * @date 2020/8/17
 */
abstract class BaseViewModel : ViewModel(), DisposableContainer {
    private val tasks = CompositeDisposable()
    override fun onCleared() {
        super.onCleared()
        tasks.clear()
    }

    override fun add(d: Disposable): Boolean {
        return tasks.add(d)
    }

    override fun remove(d: Disposable): Boolean {
        return tasks.remove(d)
    }

    override fun delete(d: Disposable): Boolean {
        return tasks.delete(d)
    }
}