package com.syr.module_common.net.core

import android.content.Context
import android.os.Bundle
import com.alibaba.android.arouter.launcher.ARouter

/**
 * Created by songyaru on 2020/10/10.
 */
object Utils {
    fun navigation(path: String, context: Context? = null, args: Bundle = Bundle()) {
        ARouter.getInstance().build(path).with(args).navigation(context)
    }
}