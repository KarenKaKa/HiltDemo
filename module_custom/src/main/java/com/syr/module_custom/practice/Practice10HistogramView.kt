package com.syr.module_custom.practice

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View

class Practice10HistogramView : View {
    constructor(context: Context?) : super(context) {}
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs) {}
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {}

    private val mPaint = Paint()
    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)

//        综合练习
//        练习内容：使用各种 Canvas.drawXXX() 方法画直方图
        mPaint.color = Color.WHITE
        mPaint.isAntiAlias = true
        mPaint.strokeWidth = 2f

        val startX = 120f
        val startY = 200f
        val lineWidth = 800f
        val lineHeight = 500f
        val barWidth = 90f
        val barSpace = 20f
        val texts = mutableListOf("Froyo", "GB", "ICS", "JB", "KitKat", "L", "M")
        canvas?.drawLine(startX, startY, startX, startY + lineHeight, mPaint)
        canvas?.drawLine(startX, startY + lineHeight, startX + lineWidth, startY + lineHeight, mPaint)

        mPaint.color = Color.GREEN
        mPaint.style = Paint.Style.FILL

        for (i in 0 until 7) {
            canvas?.drawRect(
                startX + (i + 1) * barSpace + barWidth * i,
                startY + lineHeight - 20 * (1..10).random(),
                startX + (i + 1) * barSpace + barWidth * i + barWidth,
                startY + lineHeight,
                mPaint
            )
        }

        mPaint.color = Color.WHITE
        mPaint.textSize = 24f
        mPaint.textAlign = Paint.Align.CENTER//以startx为中心点
        texts.forEachIndexed { i, s ->
            canvas?.drawText(
                s,
                startX + (i + 1) * barSpace + barWidth * i + barWidth / 2,
                startY + lineHeight + 25,
                mPaint
            )
        }

        mPaint.textSize = 50f
        canvas?.drawText("直方图", width / 2f, startY + lineHeight + 200, mPaint)
    }
}