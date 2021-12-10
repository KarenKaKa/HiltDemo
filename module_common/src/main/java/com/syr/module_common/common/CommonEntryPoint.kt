package com.syr.module_common.common

import android.content.Context
import com.syr.module_common.CommonRepository
import dagger.hilt.EntryPoint
import dagger.hilt.InstallIn
import dagger.hilt.android.EntryPointAccessors
import dagger.hilt.components.SingletonComponent

/**
 * 外部module访问Repository入口
 */
@EntryPoint
@InstallIn(SingletonComponent::class)
interface CommonEntryPoint {
    fun getCommonReposition(): CommonRepository
}

private fun Context.getCommonEntryPoint() = EntryPointAccessors.fromApplication(this, CommonEntryPoint::class.java)

// 对外提供公共Repository
fun Context.getCommonRepository() = getCommonEntryPoint().getCommonReposition()