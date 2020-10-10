package com.syr.module_custom.practice

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View

class Practice8DrawArcView : View {
    constructor(context: Context?) : super(context) {}
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs) {}
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {}

    private val mPaint = Paint()
    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)

//        练习内容：使用 canvas.drawArc() 方法画弧形和扇形
        mPaint.color = Color.BLACK
        mPaint.isAntiAlias = true

        mPaint.style = Paint.Style.FILL
        canvas?.drawArc(300f, 300f, 300 + 500f, 300 + 300f, 20f, 140f, false, mPaint)//弧形
        canvas?.drawArc(300f, 300f, 300 + 500f, 300 + 300f, -100f, 80f, true, mPaint)//扇形

        mPaint.style = Paint.Style.STROKE
        mPaint.strokeWidth = 2f
        canvas?.drawArc(300f, 300f, 300 + 500f, 300 + 300f, 180f, 50f, false, mPaint)//狐线
    }
}