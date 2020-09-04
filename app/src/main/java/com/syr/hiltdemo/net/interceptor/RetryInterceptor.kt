package com.syr.hiltdemo.net.interceptor

import android.util.Log
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response

/**
 * @author songyaru
 * @date 2020/4/22
 */
class RetryInterceptor(private val maxRetries: Int = 3, private val retryDelaySecond: Int = 2) :
    Interceptor {
    private val TAG = this.javaClass.simpleName
    private var retryCount = 0

    override fun intercept(chain: Interceptor.Chain): Response {
        val request: Request = chain.request()
        var response = chain.proceed(request)
        try {
            while (!response.isSuccessful && ++retryCount <= maxRetries) {
                Log.d(
                    TAG,
                    "Observable get error, retry count $retryCount  retryDelaySecond=$retryDelaySecond"
                )
                Thread.sleep((retryDelaySecond * 1000).toLong())
                response = chain.proceed(request)
            }
        } catch (e: Exception) {
            return response
        }
        return response
    }
}