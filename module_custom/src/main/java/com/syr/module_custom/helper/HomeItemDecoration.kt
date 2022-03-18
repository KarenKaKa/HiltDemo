package com.syr.module_custom.helper

import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ItemDecoration
import com.syr.module_custom.R
import kotlin.math.roundToInt

class HomeItemDecoration : ItemDecoration() {
    private val mBounds = Rect()
    private val mPaint = Paint()

    override fun onDraw(c: Canvas, parent: RecyclerView, state: RecyclerView.State) {
        if (parent.layoutManager == null) {
            return
        }
        drawVertical(c, parent)
        drawHorizontal(c, parent)
    }

    private fun drawVertical(canvas: Canvas, parent: RecyclerView) {
        canvas.save()
        val childCount = parent.childCount
        for (i in 0 until childCount) {
            val child = parent.getChildAt(i)

            val position = parent.getChildAdapterPosition(child)
            if (parent.adapter?.getItemViewType(position) != R.layout.layout_title) {
                parent.getDecoratedBoundsWithMargins(child, mBounds)
                val bottom = mBounds.bottom + Math.round(child.translationY)
                val top = bottom - SPACE
                canvas.drawRect(
                    child.left.toFloat(),
                    top.toFloat(),
                    child.right.toFloat(),
                    bottom.toFloat(),
                    mPaint
                )
            }
        }
        canvas.restore()
    }

    private fun drawHorizontal(canvas: Canvas, parent: RecyclerView) {
        canvas.save()
        val childCount = parent.childCount
        for (i in 0 until childCount) {
            val child = parent.getChildAt(i)

            val position = parent.getChildAdapterPosition(child)
            if (parent.adapter?.getItemViewType(position) != R.layout.layout_title) {
                parent.layoutManager?.getDecoratedBoundsWithMargins(child, mBounds)
                val right = mBounds.right + child.translationX.roundToInt()
                val left = right - SPACE
                canvas.drawRect(
                    left.toFloat(),
                    child.top.toFloat(),
                    right.toFloat(),
                    child.bottom.toFloat() + SPACE,
                    mPaint
                )
            }
        }
        canvas.restore()
    }

    override fun getItemOffsets(
        outRect: Rect, view: View, parent: RecyclerView,
        state: RecyclerView.State
    ) {
        val position = parent.getChildAdapterPosition(view)
        if (parent.adapter?.getItemViewType(position) == R.layout.layout_title) {
            outRect.set(0, 0, 0, 0)
        } else {
            outRect.set(0, 0, SPACE, SPACE)
        }
    }

    companion object {
        private const val SPACE = 1
    }

    init {
        mPaint.isAntiAlias = true
        mPaint.color = Color.parseColor("#26000000")
    }
}