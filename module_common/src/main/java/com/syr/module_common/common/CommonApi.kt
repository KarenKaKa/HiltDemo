package com.syr.module_common.common

import retrofit2.http.GET

interface CommonApi {

    @GET("https://www.wanandroid.com/article/list/0/json")
//    @GET("/users")
    suspend fun getArticles(): ArticlesResp
}