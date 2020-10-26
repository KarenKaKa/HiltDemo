package com.syr.module_shape

import android.graphics.Color
import android.os.Bundle
import android.view.ViewGroup
import com.alibaba.android.arouter.facade.annotation.Route
import com.syr.module_common.base.BaseActivity
import com.syr.module_common.net.core.RouterHub
import com.syr.module_common.utils.dp
import kotlinx.android.synthetic.main.activity_shape.*

/**
 * Created by songyaru on 2020/10/22.
 */
@Route(path = RouterHub.SHAPE_SHAPEACTIVITY)
class ShapeActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_shape)

        tvGetGradientDrawable.background = ShapeUtils.getGradientDrawable(Color.RED, leftCornerRadius = 10.dp)
        tvGetGradientDrawable2.background = ShapeUtils.getGradientDrawable(
            Color.RED, bottomCornerRadius = 10.dp,
            borderWidth = 2.dp.toInt(), borderColor = Color.BLACK
        )
        tvGetShapeDrawable.background = ShapeUtils.getShapeDrawable()
        tvGetShapeDrawable2.background = ShapeUtils.getShapeDrawable2()
        tvGetShapeDrawable3.background = ShapeUtils.getShapeDrawable3()
        (tvGetShadowDrawable.parent as? ViewGroup)?.clipChildren = false
        tvGetShadowDrawable.background = ShapeUtils.getShadowDrawable(this)
    }
}