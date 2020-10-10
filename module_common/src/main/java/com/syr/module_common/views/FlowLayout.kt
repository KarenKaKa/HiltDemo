package com.syr.module_common.views

import android.content.Context
import android.graphics.Canvas
import android.util.AttributeSet
import android.util.Log
import android.view.View
import android.view.ViewGroup
import androidx.core.view.children
import kotlin.math.max

/**
 * https://www.bilibili.com/video/BV1Ka4y1j7HA?p=4
 * 自定义View主要实现onMesure + onDraw
 * 自定义ViewGroup主要实现onMesure + onLayout
 * Created by songyaru on 2020/9/17.
 */
class FlowLayout : ViewGroup {

    val HORIZONTAL = 0
    val VERTICAL = 1

    //    @IntDef(NO_1)
    private class Navigation

    private var mHorizontalSpacing = 0//子view的间距
    private var mVerticalSpacing = 0//子view的间距

    private val allLines = mutableListOf<List<View>>()//
    private val lineHeights = mutableListOf<Int>()//

    //代码中手动创建FlowLayout
    constructor(context: Context?) : super(context)

    //反射创建FlowLayout，例如在xml中写了布局，编译后会通过反射此构造方法创建
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs) {
        getAttrs(attrs, 0)
    }

    private val s = ""

    //主题
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) :
            super(context, attrs, defStyleAttr) {
        getAttrs(attrs, defStyleAttr)
    }

    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int, defStyleRes: Int) :
            super(context, attrs, defStyleAttr, defStyleRes) {
        getAttrs(attrs, defStyleAttr)

        val b = ::getAttrs


    }

    private fun getAttrs(attrs: AttributeSet?, defStyleAttr: Int) {
//        val a = context.obtainStyledAttributes(attrs, R.styleable.ActionBar, defStyleAttr, 0)
//        mHorizontalSpacing = a.getDimensionPixelSize(R.styleable.ActivityNavigator_action, 0)
    }

    //测量View的大小
    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)

        var lineViews = mutableListOf<View>()//保存一行中的所有view
        var lineWidthUsed = 0//记录这行已经使用了多宽的size
        var lineHeight = 0//一行的行高

        val selfWidth = MeasureSpec.getSize(widthMeasureSpec)
        val selfHeight = MeasureSpec.getSize(heightMeasureSpec)
        val widthMode = MeasureSpec.getMode(widthMeasureSpec)
        val heightMode = MeasureSpec.getMode(heightMeasureSpec)

        var parentNeedWidth = 0//measure过程中，子view要求的父ViewGroup的宽
        var parentNeedHeight = 0//measure过程中，子view要求的父ViewGroup的高

        children.forEach {
            val childWithMeasureSpec = getChildMeasureSpec(
                widthMeasureSpec, paddingLeft + paddingRight, it.layoutParams.width
            )
            val childHeightMeasureSpec = getChildMeasureSpec(
                heightMeasureSpec, paddingTop + paddingBottom, it.layoutParams.height
            )
            it.measure(childWithMeasureSpec, childHeightMeasureSpec)
            //获取子view的宽高
            val childMeasureWidth = it.measuredWidth
            val childMeasureHeight = it.measuredHeight

//            Log.e("okhttp", "childMeasureWidth=$childMeasureWidth lineWidthUsed=${lineWidthUsed} mHorizontalSpacing=${mHorizontalSpacing} total=${childMeasureWidth + lineWidthUsed + mHorizontalSpacing} selfWidth=${selfWidth} ")
            if (childMeasureWidth + lineWidthUsed + mHorizontalSpacing > selfWidth) {
                allLines.add(lineViews)
                lineHeights.add(lineHeight)

                parentNeedHeight += lineHeight + mVerticalSpacing
                parentNeedWidth = max(parentNeedWidth, lineWidthUsed + mHorizontalSpacing)

                lineViews = mutableListOf()
                lineWidthUsed = 0
                lineHeight = 0
            }

            lineViews.add(it)
            lineWidthUsed += childMeasureWidth + mHorizontalSpacing
            lineHeight = max(lineHeight, childMeasureHeight)

//            parentNeedHeight += lineHeight + mVerticalSpacing
//            parentNeedWidth = max(parentNeedWidth, lineWidthUsed + mHorizontalSpacing)
        }

        val realWidth = if (widthMode == MeasureSpec.EXACTLY) selfWidth else parentNeedWidth
        val realHeight = if (heightMode == MeasureSpec.EXACTLY) selfHeight else parentNeedHeight

        //viewgroup测量自己
        setMeasuredDimension(realWidth, realHeight)
    }

    //确定子View的布局
    override fun onLayout(changed: Boolean, l: Int, t: Int, r: Int, b: Int) {
        //l  左边距离父布局的左边
        //t  上边距离父布局的上边
        //r  右边距离父布局的左边（注意不是右边）
        //b  下边距离父布局的上边（注意不是下边）
        var curL = paddingLeft
        var curT = paddingTop

        allLines.forEachIndexed { i, lineViews ->
            lineViews.forEachIndexed { j, lineView ->
                Log.e(
                    "okhttp",
                    "curL=$curL curT=${curT} measuredWidth=${lineView.measuredWidth}  measuredHeight=${lineView.measuredHeight} "
                )
                lineView.layout(
                    curL, curT,
                    curL + lineView.measuredWidth,
                    curT + lineView.measuredHeight
                )
                curL = right + mHorizontalSpacing
            }
            curL = paddingLeft
            curT += lineHeights[i] + mVerticalSpacing
        }
    }

    //绘制内容
    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
    }
}