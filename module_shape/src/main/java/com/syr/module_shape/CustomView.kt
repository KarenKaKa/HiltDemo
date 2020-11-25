package com.syr.module_shape

import android.content.Context
import android.util.AttributeSet
import android.util.Log
import android.view.MotionEvent
import android.view.View

/**
 * Created by songyaru on 2020/11/12.
 */
class CustomView : View {
    constructor(context: Context?) : super(context)
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr)
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int, defStyleRes: Int) : super(
        context, attrs, defStyleAttr, defStyleRes
    )

    var viewDis: Boolean? = null
    var viewTou: Boolean? = null

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        Log.e("okhttp", "View onTouchEvent event=${event?.action}")
        viewTou?.let { return it }
        return super.onTouchEvent(event)
    }

    override fun dispatchTouchEvent(event: MotionEvent?): Boolean {
        Log.e("okhttp", "View dispatchTouchEvent event=${event?.action}")
        viewDis?.let { return it }
        return super.dispatchTouchEvent(event)
    }
}