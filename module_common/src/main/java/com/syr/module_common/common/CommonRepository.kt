package com.syr.module_common

import com.syr.module_common.base.BaseRepository
import com.syr.module_common.base.ResultData
import com.syr.module_common.common.CommonApi
import javax.inject.Inject

/**
 * 公共请求方法
 */
class CommonRepository @Inject constructor(
    private val api: CommonApi
) : BaseRepository() {

    // module公共方法
    suspend fun getArticles() = createCallWithoutIntercept {
        val repository = api.getArticles()
        return@createCallWithoutIntercept ResultData.Success(repository)
    }

    // module私有方法
    internal suspend fun getArticleList() = createCallWithoutIntercept {
        val repository = api.getArticles()
        return@createCallWithoutIntercept ResultData.Success(repository)
    }
}