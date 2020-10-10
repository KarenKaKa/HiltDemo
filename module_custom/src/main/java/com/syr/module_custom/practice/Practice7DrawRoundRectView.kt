package com.syr.module_custom.practice

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View

class Practice7DrawRoundRectView : View {
    constructor(context: Context?) : super(context) {}
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs) {}
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {}

    private val mPaint = Paint()
    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)

//        练习内容：使用 canvas.drawRoundRect() 方法画圆角矩形
        mPaint.color = Color.BLACK
        mPaint.isAntiAlias = true
        canvas?.drawRoundRect(300f, 300f, 300 + 300f, 300 + 150f, 20f, 20f, mPaint)
    }
}