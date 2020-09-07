package com.syr.hiltdemo.base

import java.io.IOException

/**
 * @author songyaru
 * @date 2020/8/17
 */
abstract class BaseRepository {
    fun <T> createCall(repository: BaseResp<T>): ResultData<T> {
        return try {
            return if (repository.code == 2000 && null != repository.data)
                ResultData.Success(repository.data)
            else ResultData.Error(
                IOException(
                    repository.msg ?: "Request successful, request code not success code"
                )
            )
        } catch (e: Exception) {
            ResultData.Error(e)
        }
    }

    //Do not intercept success code
    suspend fun <T> createCallWithoutIntercept(call: suspend () -> ResultData<T>): ResultData<T> {
        return try {
            call()
        } catch (e: Exception) {
            ResultData.Error(e)
        }
    }
}