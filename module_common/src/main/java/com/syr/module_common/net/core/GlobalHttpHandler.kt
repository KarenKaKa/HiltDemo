package com.syr.module_common.net.core

import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response

/**
 * @author songyaru
 * @date 2020/4/24
 */
interface GlobalHttpHandler {
    fun onHttpResultResponse(httpResult: String?, chain: Interceptor.Chain?, response: Response?): Response?

    fun onHttpRequestBefore(chain: Interceptor.Chain?, request: Request?): Request?
}