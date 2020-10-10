package com.syr.module_custom.practice

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Path
import android.util.AttributeSet
import android.view.View

class Practice11PieChartView : View {
    constructor(context: Context?) : super(context) {}
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs) {}
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {}

    private val mPaint = Paint()
    private val textPaint = Paint()
    private val path = Path()
    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
//        综合练习
//        练习内容：使用各种 Canvas.drawXXX() 方法画饼图

        val pointX = 400f
        val pointy = 400f
        val radius = 200f

        mPaint.color = Color.RED
        mPaint.isAntiAlias = true
        mPaint.style = Paint.Style.FILL
        canvas?.drawArc(pointX - radius, pointy - radius, pointX + radius, pointy + radius, 180f, 120f, true, mPaint)

        mPaint.color = Color.YELLOW
        canvas?.drawArc(
            pointX - radius + 15, pointy - radius + 15,
            pointX + radius + 15, pointy + radius + 15,
            -60f, 60f, true, mPaint
        )

        mPaint.color = Color.LTGRAY
        canvas?.drawArc(
            pointX - radius + 15, pointy - radius + 15,
            pointX + radius + 15, pointy + radius + 15,
            2f, 8f, true, mPaint
        )
        mPaint.color = Color.CYAN
        canvas?.drawArc(
            pointX - radius + 15, pointy - radius + 15,
            pointX + radius + 15, pointy + radius + 15,
            12f, 10f, true, mPaint
        )
        mPaint.color = Color.GREEN
        canvas?.drawArc(
            pointX - radius + 15, pointy - radius + 15,
            pointX + radius + 15, pointy + radius + 15,
            25f, 60f, true, mPaint
        )
        mPaint.color = Color.BLUE
        canvas?.drawArc(
            pointX - radius + 15, pointy - radius + 15,
            pointX + radius + 15, pointy + radius + 15,
            88f, 92f, true, mPaint
        )

        textPaint.color = Color.WHITE
        textPaint.textSize = 28f
        textPaint.textAlign = Paint.Align.CENTER

        mPaint.color = Color.WHITE
        mPaint.style = Paint.Style.STROKE
        mPaint.strokeWidth = 2f

        canvas?.drawText("Lollipop", 100f, 205f, textPaint)
        path.moveTo(150f, 200f)
        path.lineTo(250f, 200f)
        path.rLineTo(30f, 30f)
        canvas?.drawPath(path, mPaint)

    }
}