package com.syr.module_common.net.core

import android.net.ParseException
import com.google.gson.JsonIOException
import com.google.gson.JsonParseException
import org.json.JSONException
import retrofit2.HttpException
import java.net.SocketTimeoutException
import java.net.UnknownHostException
import javax.inject.Inject
import javax.inject.Singleton

/**
 * 全局错误处理
 * @author songyaru
 * @date 2020/4/24
 */
@Singleton
class ResponseErrorHandler @Inject constructor() {

    fun handleResponseError(e: Throwable): String {
        //这里不光只能打印错误, 还可以根据不同的错误做出不同的逻辑处理
        //这里只是对几个常用错误进行简单的处理, 展示这个类的用法, 在实际开发中请您自行对更多错误进行更严谨的处理
        return when (e) {
            is UnknownHostException -> "网络不可用"
            is SocketTimeoutException -> "请求网络超时"
            is HttpException -> convertStatusCode(e)
            is JsonParseException, is ParseException,
            is JSONException, is JsonIOException -> "数据解析错误"
            else -> "未知错误"
        }
    }

    private fun convertStatusCode(httpException: HttpException): String {
        return when (httpException.code()) {
            500 -> "服务器发生错误"
            404 -> "请求地址不存在"
            403 -> "请求被服务器拒绝"
            401 -> "未授权"
            307 -> "请求被重定向到其他页面"
            else -> httpException.message() ?: "未知错误"
        }
    }
}