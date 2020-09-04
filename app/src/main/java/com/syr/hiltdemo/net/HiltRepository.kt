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
        createCall {
            val repository = api.userIdentityStatus(parameter)
            if (repository.code == 2000)
                return@createCall ResultData.Success(repository.data)
            else return@createCall ResultData.Error(IOException("Request successful, request code not success code"))
        }


    suspend fun timelineJson() = createCall {
        val repository = api.timelineJson()
        return@createCall ResultData.Success(repository)
    }
}