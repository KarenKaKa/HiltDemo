package com.syr.module_custom.practice

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View

class Practice6DrawLineView : View {
    constructor(context: Context?) : super(context) {}
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs) {}
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {}

    private val mPaint = Paint()
    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)

//        练习内容：使用 canvas.drawLine() 方法画直线
        mPaint.color = Color.BLACK
        mPaint.isAntiAlias = true
        mPaint.strokeWidth = 10f
        canvas?.drawLine(400f, 300f, 400 + 300f, 300 + 250f, mPaint)
    }
}