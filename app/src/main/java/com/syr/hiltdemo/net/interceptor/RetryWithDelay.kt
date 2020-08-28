package com.syr.hiltdemo.net.interceptor

import android.util.Log
import io.reactivex.Observable
import io.reactivex.ObservableSource
import io.reactivex.functions.Function
import java.io.IOException
import java.util.concurrent.TimeUnit

/**
 * 错误重连
 * @author songyaru
 * @date 2020/8/28
 */
class RetryWithDelay(private val maxRetries: Int, private val retryDelaySecond: Int) :
    Function<Observable<Throwable?>?, ObservableSource<*>?> {
    private val TAG = this.javaClass.simpleName
    private var retryCount = 0

    override fun apply(throwableObservable: Observable<Throwable?>): ObservableSource<*> {
        return throwableObservable.flatMap { throwable ->
            if (throwable is IOException && ++retryCount <= maxRetries) {
                // When this Observable calls onNext, the original Observable will be retried (i.e. re-subscribed).
                Log.d(
                    TAG,
                    "Observable get error, it will try after $retryDelaySecond second, retry count $retryCount"
                )
                Observable.timer(retryDelaySecond.toLong(), TimeUnit.SECONDS)
            } else {
                Observable.error<Any>(throwable)
            }
        }
    }
}