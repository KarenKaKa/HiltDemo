package com.syr.module_custom.di

import com.syr.module_common.BuildConfig
import com.syr.module_common.net.ApiFactory
import com.syr.module_custom.ProductService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

/**
 * Module to tell Hilt how to provide instances of types that cannot be constructor-injected.
 *
 * @author songyaru
 * @date 2020/8/17
 */
@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Singleton
    @Provides
    fun provideProductApiService(apiFactory: ApiFactory): ProductService {
        return apiFactory.createApiService(BuildConfig.BASE_URL, ProductService::class.java)
    }
}