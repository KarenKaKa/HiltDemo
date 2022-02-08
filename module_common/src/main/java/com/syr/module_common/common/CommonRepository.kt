package com.syr.module_common.common

import com.syr.module_common.base.BaseRepository
import com.syr.module_common.base.ResultData
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.flow
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

    val getArticlesFlow = flow {
        emit(api.getArticles()) // Emits the result of the request to the flow
        delay(1000) // Suspends the coroutine for some time
    }

    // module私有方法
    internal suspend fun getArticleList() = createCallWithoutIntercept {
        val repository = api.getArticles()
        return@createCallWithoutIntercept ResultData.Success(repository)
    }
}