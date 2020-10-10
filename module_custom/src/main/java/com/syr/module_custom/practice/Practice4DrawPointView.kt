package com.syr.module_custom.practice

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View

class Practice4DrawPointView : View {
    constructor(context: Context?) : super(context) {}
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs) {}
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context, attrs, defStyleAttr
    )

    private val mPaint = Paint()
    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)

//        练习内容：使用 canvas.drawPoint() 方法画点
//        一个圆点，一个方点
//        圆点和方点的切换使用 paint.setStrokeCap(cap)：`ROUND` 是圆点，`BUTT` 或 `SQUARE` 是方点

        mPaint.color = Color.BLACK

        mPaint.strokeWidth = 80f
        mPaint.strokeCap = Paint.Cap.ROUND
        canvas?.drawPoint(width / 2 - 100 - 80f, 300f, mPaint)

        mPaint.strokeCap = Paint.Cap.SQUARE
        canvas?.drawPoint(width / 2 + 100 + 80f, 300f, mPaint)


        mPaint.strokeCap = Paint.Cap.BUTT
        canvas?.drawPoint(width / 2 - 100 - 80f, 300f + 80 + 80 + 100, mPaint)
    }
}