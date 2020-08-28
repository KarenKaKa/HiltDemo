package com.syr.hiltdemo.base

import com.syr.hiltdemo.net.interceptor.RetryWithDelay
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

/**
 * @author songyaru
 * @date 2020/8/17
 */
abstract class BaseRepository {
    fun <R> createCall(observable: Observable<R>): Observable<R> {
        return observable.subscribeOn(Schedulers.io())
            .retryWhen(RetryWithDelay(3, 2))
            .observeOn(AndroidSchedulers.mainThread())
    }
}