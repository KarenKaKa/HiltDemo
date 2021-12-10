package com.syr.module_common.di

import com.syr.module_common.BuildConfig
import com.syr.module_common.common.CommonApi
import com.syr.module_common.net.ApiFactory
import com.syr.module_common.net.interceptor.RetryInterceptor
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

/**
 * Module to tell Hilt how to provide instances of types that cannot be constructor-injected.
 */
@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun provideApiFactory(): ApiFactory {
        return ApiFactory(listOf(RetryInterceptor()))//自定义拦截器 HeaderInterceptor(),
    }

    @Singleton
    @Provides
    fun provideApiService(apiFactory: ApiFactory): CommonApi {
        return apiFactory.createApiService(BuildConfig.BASE_URL, CommonApi::class.java)
    }
}