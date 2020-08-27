package com.syr.hiltdemo.net.core

import android.net.ParseException
import com.google.gson.JsonIOException
import com.google.gson.JsonParseException
import org.json.JSONException
import retrofit2.HttpException
import java.net.SocketTimeoutException
import java.net.UnknownHostException

/**
 * 全局错误处理
 * @author songyaru
 * @date 2020/4/24
 */
class ResponseErrorHandler{
    companion object {
        val instance by lazy(mode = LazyThreadSafetyMode.SYNCHRONIZED) {
            ResponseErrorHandler()
        }
    }

    fun handleResponseError(e: Throwable): String {
        //这里不光只能打印错误, 还可以根据不同的错误做出不同的逻辑处理
        //这里只是对几个常用错误进行简单的处理, 展示这个类的用法, 在实际开发中请您自行对更多错误进行更严谨的处理
        var msg = "未知错误"
        msg = if (e is UnknownHostException) {
            "网络不可用"
        } else if (e is SocketTimeoutException) {
            "请求网络超时"
        } else if (e is HttpException) {
            convertStatusCode(e)
        } else if (e is JsonParseException || e is ParseException || e is JSONException || e is JsonIOException) {
            "数据解析错误"
        } else {
            "网络错误"
        }
        return msg
    }

    private fun convertStatusCode(httpException: HttpException): String {
        return if (httpException.code() == 500) {
            "服务器发生错误"
        } else if (httpException.code() == 404) {
            "请求地址不存在"
        } else if (httpException.code() == 403) {
            "请求被服务器拒绝"
        } else if (httpException.code() == 401) {
            "未授权"
        } else if (httpException.code() == 307) {
            "请求被重定向到其他页面"
        } else {
            httpException.message() ?: ""
        }
    }
}