package com.syr.hiltdemo.utils

import android.content.Context
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.LayoutRes
import com.syr.module_common.R

/**
 * View 扩展方法
 * @author songyaru
 * @date 2020/4/26
 */

fun View.hideKeyboard() {
    val inputManager = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    inputManager.hideSoftInputFromWindow(windowToken, 0)
}

fun View.showKeyboard() {
    isFocusable = true
    isFocusableInTouchMode = true
    requestFocus()
    val inputManager = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    postDelayed({
        inputManager.showSoftInput(this, InputMethodManager.SHOW_FORCED)
    }, 100)
}

fun Context.showToast(message: String) {
    val layout = LayoutInflater.from(this).inflate(R.layout.custom_toast, null) as? TextView
    layout?.text = message
    with(Toast(this)) {
        setGravity(Gravity.CENTER_VERTICAL, 0, 0)
        duration = Toast.LENGTH_SHORT
        view = layout
        show()
    }
}