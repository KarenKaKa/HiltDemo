package com.syr.hiltdemo.net.core

import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response

/**
 * 对返回数据进行全局拦截或者在请求之前添加操作
 * @author songyaru
 * @date 2020/4/23
 */
class ResponseHandler : GlobalHttpHandler {
    override fun onHttpRequestBefore(chain: Interceptor.Chain?, request: Request?): Request? {
        // 如果需要再请求服务器之前做一些操作, 则重新返回一个做过操作的的 Request 如增加 Header, 不做操作则直接返回参数 request
        return request
    }

    override fun onHttpResultResponse(httpResult: String?, chain: Interceptor.Chain?, response: Response?): Response? {
        return response
    }
}