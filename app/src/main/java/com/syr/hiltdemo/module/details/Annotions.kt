package com.syr.hiltdemo.module.details

import androidx.annotation.StringDef


// 页面来源
@StringDef(SourceFrom.ARTICLES, SourceFrom.DETAILS)
@Retention(AnnotationRetention.SOURCE)
annotation class SourceFrom {
    companion object {
        const val ARTICLES = "1"
        const val DETAILS = "2"
    }
}