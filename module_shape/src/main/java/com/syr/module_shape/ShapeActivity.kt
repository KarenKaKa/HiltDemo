package com.syr.module_shape

import android.graphics.Color
import android.os.Bundle
import android.view.ViewGroup
import com.alibaba.android.arouter.facade.annotation.Route
import com.syr.module_common.base.BaseActivity
import com.syr.module_common.net.core.RouterHub
import com.syr.module_common.utils.dp
import com.syr.module_shape.databinding.ActivityShapeBinding

/**
 * Created by songyaru on 2020/10/22.
 */
@Route(path = RouterHub.SHAPE_SHAPEACTIVITY)
class ShapeActivity : BaseActivity() {
    private lateinit var binding:ActivityShapeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityShapeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.tvGetGradientDrawable.background = ShapeUtils.getGradientDrawable(Color.RED, leftCornerRadius = 10.dp)
        binding.tvGetGradientDrawable2.background = ShapeUtils.getGradientDrawable(
            Color.RED, bottomCornerRadius = 10.dp,
            borderWidth = 2.dp.toInt(), borderColor = Color.BLACK
        )
        binding.tvGetShapeDrawable.background = ShapeUtils.getShapeDrawable()
        binding.tvGetShapeDrawable2.background = ShapeUtils.getShapeDrawable2()
        binding.tvGetShapeDrawable3.background = ShapeUtils.getShapeDrawable3()
        (binding.tvGetShadowDrawable.parent as? ViewGroup)?.clipChildren = false
        binding.tvGetShadowDrawable.background = ShapeUtils.getShadowDrawable(this)
    }
}