package com.syr.module_common.utils

import androidx.annotation.IdRes
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity

// replace Fragment
fun FragmentActivity.replaceFragment(@IdRes containerId: Int, replace: Fragment) {
    supportFragmentManager.beginTransaction()
        .replace(containerId, replace, replace.javaClass.simpleName)
        .commitAllowingStateLoss()
}