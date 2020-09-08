package com.syr.hiltdemo.net

import com.syr.hiltdemo.base.BaseResp
import com.syr.hiltdemo.net.vo.IdentityInfo
import com.syr.hiltdemo.net.vo.TimeLineJson
import retrofit2.http.FieldMap
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST

/**
 * @author songyaru
 * @date 2020/8/17
 */
interface ApiService {
    @FormUrlEncoded
    @POST("/web/Agreement/getUrl")
    suspend fun userIdentityStatus(@FieldMap map: MutableMap<String, String>): BaseResp<IdentityInfo>

    @GET("timeline.json")
    suspend fun timelineJson(): TimeLineJson
}