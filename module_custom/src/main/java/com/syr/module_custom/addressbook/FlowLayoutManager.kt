package com.syr.module_custom.addressbook

import androidx.recyclerview.widget.RecyclerView

/**
 * 流式布局，自动换行
 * Created by songyaru on 2022/3/18.
 */
internal class FlowLayoutManager : RecyclerView.LayoutManager() {

    override fun isAutoMeasureEnabled(): Boolean {
        return true
    }

    override fun generateDefaultLayoutParams(): RecyclerView.LayoutParams {
        return RecyclerView.LayoutParams(
            RecyclerView.LayoutParams.WRAP_CONTENT,
            RecyclerView.LayoutParams.WRAP_CONTENT
        )
    }

    override fun onLayoutChildren(recycler: RecyclerView.Recycler?, state: RecyclerView.State?) {
        recycler ?: return
        detachAndScrapAttachedViews(recycler)

        var curLineWidth = paddingStart
        var curLineTop = paddingTop
        var lastLineMaxHeight = 0
        val maxWidth = width - paddingEnd

        for (i in 0 until itemCount) {
            val view = recycler.getViewForPosition(i)
            val params = view.layoutParams as? RecyclerView.LayoutParams
            val margins = intArrayOf(
                params?.leftMargin ?: 0,
                params?.topMargin ?: 0,
                params?.rightMargin ?: 0,
                params?.bottomMargin ?: 0,
            )
            addView(view)
            measureChildWithMargins(view, 0, 0)
            val width = getDecoratedMeasuredWidth(view) + margins[0] + margins[2]
            val height = getDecoratedMeasuredHeight(view) + margins[1] + margins[3]

            curLineWidth += width
            if (curLineWidth <= maxWidth) {//不需要换行
                layoutDecorated(
                    view,
                    curLineWidth - width + margins[0],
                    curLineTop + margins[1],
                    curLineWidth - margins[2],
                    curLineTop + height - margins[3]
                )
                // 比较当前行的最大高度
                lastLineMaxHeight = Math.max(lastLineMaxHeight, height)
            } else {
                // 换行
                curLineWidth = width + paddingStart
                if (lastLineMaxHeight == 0) {
                    lastLineMaxHeight = height
                }
                // 记录当前行top
                curLineTop += lastLineMaxHeight

                layoutDecorated(
                    view,
                    paddingStart + margins[0],
                    curLineTop + margins[1],
                    paddingStart + width - margins[2],
                    curLineTop + height - margins[3]
                )
                lastLineMaxHeight = height
            }
        }
    }
}