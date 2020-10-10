package com.syr.module_common.utils

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build

/**
 * @author songyaru
 * @date 2020/8/21
 */
object UiUtil {
    fun isAvailable(context: Context): Boolean {
        val manager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager?
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