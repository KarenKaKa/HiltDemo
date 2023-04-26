package com.syr.module_shape.shapeView

import android.content.Context
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatTextView

internal class ShapeTextView : AppCompatTextView {
    constructor(context: Context) : this(context, null)
    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0)
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context, attrs, defStyleAttr
    ) {
        val mAttrHelper = AttrHelper(context, attrs)
        background = mAttrHelper.getShapeDrawable()
    }
}