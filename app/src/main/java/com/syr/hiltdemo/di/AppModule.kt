package com.syr.hiltdemo.di

import com.syr.hiltdemo.ApiService
import com.syr.hiltdemo.BuildConfig
import com.syr.module_common.net.ApiFactory
import com.syr.module_common.net.interceptor.RetryInterceptor
import com.syr.module_custom.ProductService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import javax.inject.Singleton

/**
 * Module to tell Hilt how to provide instances of types that cannot be constructor-injected.
 *
 * @author songyaru
 * @date 2020/8/17
 */
@Module
@InstallIn(ApplicationComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun provideApiFactory(): ApiFactory {
        return ApiFactory(listOf(RetryInterceptor()))//自定义拦截器 HeaderInterceptor(),
    }

    @Singleton
    @Provides
    fun provideApiService(apiFactory: ApiFactory): ApiService {
        return apiFactory.createApiService(BuildConfig.BASE_URL, ApiService::class.java)
    }

    /**
     * 提供多种ApiService
     */
    @Singleton
    @Provides
    fun provideProductApiService(apiFactory: ApiFactory): ProductService {
        return apiFactory.createApiService(BuildConfig.BASE_URL, ProductService::class.java)
    }
}