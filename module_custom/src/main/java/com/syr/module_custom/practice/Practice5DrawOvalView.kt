package com.syr.module_custom.practice

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View

class Practice5DrawOvalView : View {
    constructor(context: Context?) : super(context) {}
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs) {}
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {}

    private val mPaint = Paint()
    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)

//        练习内容：使用 canvas.drawOval() 方法画椭圆

        mPaint.color = Color.BLACK
        mPaint.isAntiAlias = true
        canvas?.drawOval(width / 2 - 200f, 300f, width / 2 + 200f, 300 + 200f, mPaint)
    }
}