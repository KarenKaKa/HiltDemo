package com.syr.module_custom.addressbook

import android.graphics.*
import android.text.TextPaint
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.syr.module_common.utils.dp
import com.syr.module_common.utils.sp

/**
 * 通讯录 stickyHeader效果
 * Created by songyaru on 2022/3/18.
 */
internal class PinnedSectionDecoration(private var callback: ((Int) -> String)? = null) :
    RecyclerView.ItemDecoration() {

    // groupName区域高
    private val topGap = 40.dp
    private var paint = Paint()
    private var textPaint = TextPaint()

    private val dividerPaint = TextPaint()

    // 分割线高
    private val dividerHeight = 1.dp

    init {
        // groupName区域背景色
        paint.color = Color.parseColor("#1dcf9f")
        textPaint.typeface = Typeface.DEFAULT_BOLD
        textPaint.isAntiAlias = true
        // groupName字号
        textPaint.textSize = 22.sp
        // groupName字体颜色
        textPaint.color = Color.BLACK
        textPaint.textAlign = Paint.Align.LEFT

        // 分割线颜色
        dividerPaint.color = Color.parseColor("#FF0000")
    }

    override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
        super.getItemOffsets(outRect, view, parent, state)
        val position = parent.getChildAdapterPosition(view)
        val groupName = callback?.invoke(position)
        if (groupName.isNullOrBlank()) return
        // 预留group绘制区域
        if (isFirstInGroup(position)) {
            outRect.top = topGap.toInt()
        } else {
            outRect.top = 0
        }
    }

    override fun onDrawOver(c: Canvas, parent: RecyclerView, state: RecyclerView.State) {
        super.onDrawOver(c, parent, state)

        val itemCount = state.itemCount
        val childCount = parent.childCount
        val left = parent.paddingLeft
        val right = parent.width - parent.paddingRight
        val nameLeft = left + 16.dp

        var preGroupName: String
        var groupName = ""
        for (i in 0 until childCount) {
            val child = parent.getChildAt(i)
            val position = parent.getChildAdapterPosition(child)
            preGroupName = groupName
            groupName = callback?.invoke(position).orEmpty()
            if (groupName.isBlank() || groupName == preGroupName) continue
            val name = callback?.invoke(position)?.uppercase().orEmpty()
            if (name.isBlank()) continue
            val viewBottom = child.bottom
            var textY = Math.max(topGap, child.top.toFloat())
            // 下一个group和当前不一样移动当前
            if (position + 1 < itemCount) {
                val nextGroupName = callback?.invoke(position + 1)
                // 组内最后一个child进入了header
                if (nextGroupName != groupName && viewBottom < textY) {
                    textY = viewBottom.toFloat()
                }
            }
            c.drawRect(left.toFloat(), textY - topGap, right.toFloat(), textY, paint)
            // 计算baseline
            val fontMetrics = textPaint.fontMetrics
            val distance = (fontMetrics.bottom - fontMetrics.top) / 2 - fontMetrics.bottom
            val baseline = textY - topGap / 2 + distance
            c.drawText(name, nameLeft, baseline, textPaint)
        }
    }

    override fun onDraw(c: Canvas, parent: RecyclerView, state: RecyclerView.State) {
        super.onDraw(c, parent, state)
        val childCount = parent.childCount
        // 10.dp为分割线左间距
        val left = parent.paddingLeft + 10.dp
        // 10.dp为分割线右间距
        val right = parent.width - parent.paddingRight - 10.dp

        for (i in 0 until childCount - 1) {
            val child = parent.getChildAt(i)
            val top = child.bottom.toFloat()
            val bottom = child.bottom + dividerHeight
            c.drawRect(left, top, right, bottom, dividerPaint)
        }
    }

    private fun isFirstInGroup(position: Int): Boolean {
        return if (position == 0) {
            true
        } else {
            val prevGroupName = callback?.invoke(position - 1)
            val groupName = callback?.invoke(position)
            prevGroupName != groupName
        }
    }
}