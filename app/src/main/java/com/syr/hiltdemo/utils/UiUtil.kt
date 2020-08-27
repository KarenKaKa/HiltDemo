package com.syr.hiltdemo.utils

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import com.syr.hiltdemo.HiltApp

/**
 * @author songyaru
 * @date 2020/8/21
 */
object UiUtil {
    fun isAvailable(): Boolean {
        val manager =
            HiltApp.instance.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager?
        manager?.let {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                val networkCapabilities =
                    it.getNetworkCapabilities(it.activeNetwork)
                if (networkCapabilities != null) {
                    return (networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI)
                            || networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR)
                            || networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET))
                }
            } else {
                val networkInfo = it.activeNetworkInfo
                return networkInfo != null && networkInfo.isAvailable
            }
        }
        return false
    }
}