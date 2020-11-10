package com.syr.module_common.di

import com.syr.module_common.net.ApiFactory
import com.syr.module_common.net.interceptor.RetryInterceptor
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
}