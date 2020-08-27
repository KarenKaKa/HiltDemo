package com.syr.hiltdemo

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

/**
 * @author songyaru
 * @date 2020/8/17
 */
@HiltAndroidApp
class HiltApp : Application() {
    companion object {
        lateinit var instance: HiltApp
            private set
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
    }
}