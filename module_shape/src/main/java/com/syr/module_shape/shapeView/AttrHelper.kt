package com.syr.module_shape.shapeView

import android.content.Context
import android.graphics.drawable.Drawable
import android.graphics.drawable.GradientDrawable
import android.util.AttributeSet
import com.syr.module_shape.R

/**
 * Created by songyaru on 2023/4/26.
 */
internal class AttrHelper(context: Context, attrs: AttributeSet?) {
    private val cornerRadii = FloatArray(8)
    private var solidColor = -1
    private var strokeColor = -1
    private var strokeWidth = 0

    init {
        val typedArray = context.obtainStyledAttributes(attrs, R.styleable.ShapeView)
        val radius = typedArray.getDimension(R.styleable.ShapeView_corners, 0f).coerceAtLeast(0f)
        var topLeftRadius = typedArray.getDimension(R.styleable.ShapeView_topLeftRadius, 0f)
        var topRightRadius = typedArray.getDimension(R.styleable.ShapeView_topRightRadius, 0f)
        var bottomLeftRadius = typedArray.getDimension(R.styleable.ShapeView_bottomLeftRadius, 0f)
        var bottomRightRadius = typedArray.getDimension(R.styleable.ShapeView_bottomRightRadius, 0f)
        solidColor = typedArray.getColor(R.styleable.ShapeView_solidColor, -1)
        strokeColor = typedArray.getColor(R.styleable.ShapeView_strokeColor, -1)
        strokeWidth = typedArray.getDimensionPixelSize(R.styleable.ShapeView_strokeWidth, 0)
        typedArray.recycle()

        topLeftRadius = if (topLeftRadius >= 0) topLeftRadius else radius
        topRightRadius = if (topRightRadius >= 0) topRightRadius else radius
        bottomLeftRadius = if (bottomLeftRadius >= 0) bottomLeftRadius else radius
        bottomRightRadius = if (bottomRightRadius >= 0) bottomRightRadius else radius

        cornerRadii[0] = topLeftRadius
        cornerRadii[1] = topLeftRadius
        cornerRadii[2] = topRightRadius
        cornerRadii[3] = topRightRadius
        cornerRadii[4] = bottomRightRadius
        cornerRadii[5] = bottomRightRadius
        cornerRadii[6] = bottomLeftRadius
        cornerRadii[7] = bottomLeftRadius
    }

    fun getShapeDrawable(): Drawable {
        val gradientDrawable = GradientDrawable()
        if (solidColor != -1) {
            gradientDrawable.setColor(solidColor)
        }
        gradientDrawable.cornerRadii = cornerRadii
        if (strokeColor != -1 && strokeWidth > 0) {
            gradientDrawable.setStroke(strokeWidth, strokeColor)
        }
        return gradientDrawable
    }
}