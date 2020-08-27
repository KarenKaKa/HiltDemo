package com.syr.hiltdemo.base

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
            .observeOn(AndroidSchedulers.mainThread())
    }
}