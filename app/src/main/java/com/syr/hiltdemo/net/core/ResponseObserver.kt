package com.syr.hiltdemo.net.core

import io.reactivex.Observer
import io.reactivex.disposables.Disposable
import io.reactivex.internal.disposables.DisposableHelper
import io.reactivex.internal.util.EndConsumerHelper
import java.util.concurrent.atomic.AtomicReference

/**
 * 全局拦截处理error
 * @author songyaru
 * @date 2020/4/23
 */
abstract class ResponseObserver<T> : Observer<T>, Disposable {
    private val upstream = AtomicReference<Disposable>()
    private var handledResponseError = ""

    override fun onSubscribe(d: Disposable) {
        if (EndConsumerHelper.setOnce(upstream, d, javaClass)) {
            onStart()
        }
    }

    /**
     * Called once the single upstream Disposable is set via onSubscribe.
     */
    protected open fun onStart() {}

    override fun onComplete() {
    }

    override fun onNext(t: T) {
    }

    override fun onError(e: Throwable) {
        //如果你某个地方不想使用全局错误处理,则重写 onError(Throwable) 并将 super.onError(e); 删掉
        //如果你不仅想使用全局错误处理,还想加入自己的逻辑,则重写 onError(Throwable) 并在 super.onError(e); 后面加入自己的逻辑
        handledResponseError = ResponseErrorHandler.instance.handleResponseError(e)
    }

    /**
     * 获取对Throwable处理过后的error string
     */
    protected fun getHandledResonseError(): String {
        return handledResponseError
    }

    override fun isDisposed(): Boolean {
        return upstream.get() === DisposableHelper.DISPOSED
    }

    override fun dispose() {
        DisposableHelper.dispose(upstream)
    }
}