package com.syr.hiltdemo.net

import com.syr.hiltdemo.base.BaseRepository
import com.syr.hiltdemo.base.ResultData
import java.io.IOException
import javax.inject.Inject
import javax.inject.Singleton

/**
 * @author songyaru
 * @date 2020/8/17
 */
@Singleton
class HiltRepository @Inject constructor(
    private val api: ApiService
) : BaseRepository() {
    suspend fun userIdentityStatus(parameter: MutableMap<String, String>) =
        createCallWithoutIntercept {
            val repository = api.userIdentityStatus(parameter)
            if (repository.code == 2000 && null != repository.data)
                return@createCallWithoutIntercept ResultData.Success(repository.data)
            else return@createCallWithoutIntercept ResultData.Error(
                IOException(
                    repository.msg ?: "Request successful, request code not success code"
                )
            )
        }

    suspend fun userIdentityStatus1(parameter: MutableMap<String, String>) =
        createCall { api.userIdentityStatus(parameter) }


    suspend fun timelineJson() = createCallWithoutIntercept {
        val repository = api.timelineJson()
        return@createCallWithoutIntercept ResultData.Success(repository)
    }
}