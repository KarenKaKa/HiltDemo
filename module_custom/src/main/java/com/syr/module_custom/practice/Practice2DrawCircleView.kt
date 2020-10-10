package com.syr.module_custom.practice

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View

/**
 * Created by songyaru on 2020/9/27.
 */
class Practice2DrawCircleView : View {
    constructor(context: Context?) : super(context)
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr)
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int, defStyleRes: Int) : super(
        context, attrs, defStyleAttr, defStyleRes
    )

    private val mPaint = Paint()
    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)

//        练习内容：使用 canvas.drawCircle() 方法画圆
//        一共四个圆：1.实心圆 2.空心圆 3.蓝色实心圆 4.线宽为 20 的空心圆

        val radiusWidth = width / 2
        val space = 20
        val radius = radiusWidth / 2f - space - 50
        mPaint.style = Paint.Style.FILL
        mPaint.color = Color.BLACK
        canvas?.drawCircle(radiusWidth - space - radius, radius + 35, radius, mPaint)

        mPaint.style = Paint.Style.STROKE
        mPaint.color = Color.BLACK
        mPaint.strokeWidth = 4f
        mPaint.isAntiAlias = true
        canvas?.drawCircle(radiusWidth + space + radius, radius + 35, radius - 4 + 2, mPaint)

        mPaint.style = Paint.Style.FILL
        mPaint.color = Color.BLUE
        canvas?.drawCircle(radiusWidth - space - radius, radius * 3 + 35f + 35f, radius, mPaint)

        mPaint.style = Paint.Style.STROKE
        mPaint.color = Color.BLACK
        mPaint.strokeWidth = 80f
        mPaint.isAntiAlias = true
        canvas?.drawCircle(radiusWidth + space + radius, radius * 3 + 35f + 35f, radius - 80 + 40, mPaint)
    }
}