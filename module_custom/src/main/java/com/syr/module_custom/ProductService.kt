package com.syr.module_custom

import com.syr.module_common.base.BaseResp
import retrofit2.http.FieldMap
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

/**
 * @author songyaru
 * @date 2020/8/17
 */
interface ProductService {
    @FormUrlEncoded
    @POST("/web/Agreement/getUrl")
    suspend fun userIdentityStatus(@FieldMap map: MutableMap<String, String>): BaseResp<Nothing>
}