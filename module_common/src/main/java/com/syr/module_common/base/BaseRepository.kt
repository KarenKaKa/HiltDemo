package com.syr.module_common.base

import com.syr.module_common.net.core.ResponseErrorHandler
import javax.inject.Inject

/**
 * @author songyaru
 * @date 2020/8/17
 */
abstract class BaseRepository {
    @Inject
    lateinit var responseErrorHandler: ResponseErrorHandler

    suspend fun <T> createCall(call: suspend () -> BaseResp<T>): ResultData<T> {
        return try {
            val repository = call()
            return if (repository.code == 2000 && null != repository.data)
                ResultData.Success(repository.data)
            else ResultData.ErrorMessage(
                repository.msg ?: "Request successful, request code not success code"
            )
        } catch (e: Exception) {
            ResultData.ErrorMessage(
                responseErrorHandler.handleResponseError(e)
            )
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