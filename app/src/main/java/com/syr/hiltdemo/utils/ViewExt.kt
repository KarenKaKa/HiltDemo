package com.syr.hiltdemo.utils

import android.content.Context
import android.view.View
import android.view.inputmethod.InputMethodManager

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