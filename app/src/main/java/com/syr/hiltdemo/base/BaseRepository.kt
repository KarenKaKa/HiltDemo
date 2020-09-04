package com.syr.hiltdemo.base

/**
 * @author songyaru
 * @date 2020/8/17
 */
abstract class BaseRepository {
    suspend fun <T> createCall(call: suspend () -> ResultData<T>): ResultData<T> {
        return try {
           call()
        } catch (e: Exception) {
            ResultData.Error(e)
        }
    }
}