package com.syr.module_shape

import android.content.Context
import android.graphics.Color
import android.graphics.Paint
import android.graphics.drawable.Drawable
import android.graphics.drawable.GradientDrawable
import android.os.Build
import android.view.ViewGroup
import androidx.annotation.ColorInt
import androidx.cardview.widget.CardView
import com.google.android.material.shape.*
import com.syr.module_common.utils.dp

/**
 * Created by songyaru on 2020/10/22.
 */

fun ViewGroup.shadowConfigure() {
    if (Build.VERSION.SDK_INT < Build.VERSION_CODES.P) {
        translationZ = 2.dp
        if (this is CardView) cardElevation = 4.dp
        else elevation = 4.dp
    } else {
         translationZ = 12.dp
         if (this is CardView) cardElevation = 12.dp
         else elevation = 12.dp
         outlineSpotShadowColor = context.getColor(R.color.colorAccent)
    }
}

object ShapeUtils {

    fun getGradientDrawable(
        @ColorInt bgColor: Int,
        leftCornerRadius: Float = 0f, topCornerRadius: Float = 0f,
        rightCornerRadius: Float = 0f, bottomCornerRadius: Float = 0f,
        borderWidth: Int = 0, @ColorInt borderColor: Int = -1
    ): Drawable {
        val gradientDrawable = GradientDrawable()
        gradientDrawable.setColor(bgColor)
        gradientDrawable.cornerRadii = floatArrayOf(
            leftCornerRadius, leftCornerRadius,
            topCornerRadius, topCornerRadius,
            rightCornerRadius, rightCornerRadius,
            bottomCornerRadius, bottomCornerRadius
        )
        gradientDrawable.setStroke(borderWidth, borderColor)
        return gradientDrawable
    }

    fun getShapeDrawable(): Drawable {
        val shapePathModel = ShapeAppearanceModel.builder()
//            .setAllCorners(CornerFamily.ROUNDED, 10.dp)
            .setLeftEdge(MarkerEdgeTreatment(20.dp))
            .build()
        return MaterialShapeDrawable(shapePathModel).apply {
            setTint(Color.parseColor("#FF0000"))
            paintStyle = Paint.Style.FILL_AND_STROKE
            setStroke(2.dp, Color.BLACK)
        }
    }

    fun getShapeDrawable2(): Drawable {
        val shapePathModel = ShapeAppearanceModel.builder()
            .setAllCorners(CornerFamily.ROUNDED, 10.dp)
            .setRightEdge(object : TriangleEdgeTreatment(8.dp, false) {
                override fun getEdgePath(length: Float, center: Float, interpolation: Float, shapePath: ShapePath) {
                    super.getEdgePath(length, 14.dp, interpolation, shapePath)
                }
            })
            .build()
        return MaterialShapeDrawable(shapePathModel).apply {
            setTint(Color.parseColor("#FF0000"))
            paintStyle = Paint.Style.FILL_AND_STROKE
            setStroke(2.dp, Color.BLACK)
        }
    }

    fun getShapeDrawable3(): Drawable {
        val shapePathModel = ShapeAppearanceModel.builder()
            .setTopLeftCorner(CornerFamily.ROUNDED, 10.dp)
            .setBottomRightCorner(CornerFamily.ROUNDED, 10.dp)
            .setRightEdge(object : EdgeTreatment() {
                val size = 8.dp
                override fun getEdgePath(length: Float, center: Float, interpolation: Float, shapePath: ShapePath) {
//                    shapePath.lineTo(0f, 20.dp * interpolation)
                    shapePath.lineTo(0f, 0f)

//                    shapePath.lineTo(20.dp * interpolation, -size * interpolation)
//                    shapePath.lineTo(center + size * interpolation, 0f)
                    shapePath.lineTo(length, 0f)
                }
            })
            .build()
        return MaterialShapeDrawable(shapePathModel).apply {
            setTint(Color.parseColor("#FF0000"))
            paintStyle = Paint.Style.FILL_AND_STROKE
            setStroke(2.dp, Color.BLACK)
        }
    }

    fun getShadowDrawable(context: Context): Drawable {
        val shapePathModel = ShapeAppearanceModel.builder()
            .setAllCorners(CornerFamily.ROUNDED, 4.dp)
            .build()
        return MaterialShapeDrawable(shapePathModel).apply {
            paintStyle = Paint.Style.FILL
            //背景色
            setTint(Color.WHITE)
            shadowCompatibilityMode = MaterialShapeDrawable.SHADOW_COMPAT_MODE_ALWAYS
            initializeElevationOverlay(context)
            //阴影大小
            elevation = 16.dp
            translationZ = 0f
//            setShadowColor(Color.parseColor("#CCCCCC"))
            //阴影颜色
            setShadowColor(Color.RED)
            //阴影垂直位移，越大越向下
            shadowVerticalOffset = 0
            shadowCompatRotation = 0
        }
    }

    fun getShadowDrawable1(context: Context): Drawable {
        val shapePathModel = ShapeAppearanceModel.builder()
            .setAllCorners(CornerFamily.ROUNDED, 4.dp)
            .build()
        return MaterialShapeDrawable(shapePathModel).apply {
            paintStyle = Paint.Style.FILL
            //背景色
            setTint(Color.WHITE)
            shadowCompatibilityMode = MaterialShapeDrawable.SHADOW_COMPAT_MODE_ALWAYS
            initializeElevationOverlay(context)
            //阴影大小
            elevation = 16.dp
            translationZ = 10.dp

//            setShadowColor(Color.parseColor("#33898E8D"))
            //阴影颜色
            setShadowColor(Color.RED)
            //阴影垂直位移，越大越向下
            shadowVerticalOffset = 10
            shadowCompatRotation = 0
        }
    }
}