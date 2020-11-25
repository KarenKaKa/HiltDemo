package com.syr.module_shape

import android.content.Context
import android.util.AttributeSet
import android.util.Log
import android.view.MotionEvent
import android.widget.LinearLayout

/**
 * Created by songyaru on 2020/11/12.
 */
class CustomLinearLayout : LinearLayout {
    constructor(context: Context?) : super(context)
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr)
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int, defStyleRes: Int) : super(
        context, attrs, defStyleAttr, defStyleRes
    )

    var goupInt: Boolean? = null
    var goupDis: Boolean? = null
    var goupTou: Boolean? = null

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        Log.e("okhttp", "ViewGroup onTouchEvent event=${event?.action}")
        goupTou?.let { return it }
        return super.onTouchEvent(event)
    }

    override fun onInterceptTouchEvent(event: MotionEvent?): Boolean {
        Log.e("okhttp", "ViewGroup onInterceptTouchEvent event=${event?.action}")
        goupInt?.let { return it }
        return super.onInterceptTouchEvent(event)
    }

    override fun dispatchTouchEvent(event: MotionEvent?): Boolean {
        Log.e("okhttp", "ViewGroup dispatchTouchEvent event=${event?.action}")
        goupDis?.let { return it }
        return super.dispatchTouchEvent(event)
    }
}