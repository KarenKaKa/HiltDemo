package com.syr.hiltdemo.net

import com.syr.hiltdemo.base.BaseRepository
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
    fun userIdentityStatus(parameter: MutableMap<String, String>) =
        createCall(api.userIdentityStatus(parameter))

    fun timelineJson() = createCall(api.timelineJson())
}