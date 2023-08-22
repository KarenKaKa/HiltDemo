package com.syr.module_custom.addressbook

import android.graphics.Canvas
import android.graphics.Rect
import android.graphics.Region
import android.view.View
import android.view.ViewGroup
import android.view.ViewGroup.MarginLayoutParams
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.AdapterDataObserver
import androidx.recyclerview.widget.RecyclerView.ItemDecoration
import androidx.recyclerview.widget.StaggeredGridLayoutManager

class PinnedHeaderItemDecoration(
    private val isPinnedHeader: (position: Int) -> Boolean
) : ItemDecoration() {
    private var mAdapter: RecyclerView.Adapter<*>? = null

    // 缓存的标签
    private var mPinnedHeaderView: View? = null

    // 缓存的标签位置
    private var mPinnedHeaderPosition = -1

    // 顶部标签的Y轴偏移值
    private var mPinnedHeaderOffset = 0

    // 用于锁定画布绘制范围
    private var mClipBounds: Rect? = null

    // 父布局的左间距
    private var mRecyclerViewPaddingLeft = 0

    // 父布局的顶间距
    private var mRecyclerViewPaddingTop = 0

    private var mHeaderLeftMargin = 0
    private var mHeaderTopMargin = 0
    private var mHeaderRightMargin = 0
    private var mHeaderBottomMargin = 0
    private var mFirstVisiblePosition = 0

    override fun getItemOffsets(
        outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State
    ) {
        checkCache(parent)
    }

    override fun onDraw(c: Canvas, parent: RecyclerView, state: RecyclerView.State) {
        // 检测到标签存在的时候，将标签强制固定在顶部
        createPinnedHeader(parent)
        val headerView = mPinnedHeaderView ?: return
        if (mFirstVisiblePosition >= mPinnedHeaderPosition) {
            mClipBounds = c.clipBounds
            val clipBounds = mClipBounds ?: return
            // getTop拿到的是它的原点(它自身的padding值包含在内)相对parent的顶部距离，加上它的高度后就是它的底部所处的位置
            val headEnd = headerView.top + headerView.height
            // 解决头部闪烁问题
            val belowView =
                parent.findChildViewUnder((c.width / 2).toFloat(), headEnd.toFloat()) ?: return
            val belowViewAdapterPosition = parent.getChildAdapterPosition(belowView)
            if (belowViewAdapterPosition > mFirstVisiblePosition &&
                isPinnedHeader(belowViewAdapterPosition)
            ) {
                // 如果是标签的话，缓存的标签就要同步跟此标签移动
                // 根据belowView相对顶部距离计算出缓存标签的位移
                mPinnedHeaderOffset =
                    belowView.top - (mRecyclerViewPaddingTop + headerView.height + mHeaderTopMargin)
                clipBounds.top = mRecyclerViewPaddingTop
            } else {
                mPinnedHeaderOffset = 0
                clipBounds.top = mRecyclerViewPaddingTop
            }
            // 锁定画布绘制范围，记为A
            c.clipRect(clipBounds)
        }
    }

    override fun onDrawOver(c: Canvas, parent: RecyclerView, state: RecyclerView.State) {
        val headerView = mPinnedHeaderView ?: return
        if (mFirstVisiblePosition >= mPinnedHeaderPosition) {
            val clipBounds = mClipBounds ?: return
            c.save()
            clipBounds.top = mRecyclerViewPaddingTop + mHeaderTopMargin
            // 锁定画布绘制范围，记为B
            // REVERSE_DIFFERENCE，实际上就是求得的B和A的差集范围，即B－A，只有在此范围内的绘制内容才会被显示
            // 因此,只绘制(0,0,parent.getWidth(),belowView.getTop())这个范围，然后画布移动了mPinnedHeaderTop，所以刚好是绘制顶部标签移动的范围
            // 低版本不行，换回Region.Op.UNION并集

            // 解决编译版本28修改后的抛出异常：java.lang.IllegalArgumentException: Invalid Region.Op - only INTERSECT and DIFFERENCE are allowed
            c.clipRect(clipBounds, Region.Op.INTERSECT)
            c.translate(
                (mRecyclerViewPaddingLeft + mHeaderLeftMargin).toFloat(),
                (mPinnedHeaderOffset + mRecyclerViewPaddingTop + mHeaderTopMargin).toFloat()
            )
            headerView.draw(c)
            c.restore()
        }
    }

    /**
     * 创建标签强制固定在顶部
     *
     * @param parent
     */
    private fun createPinnedHeader(parent: RecyclerView) {
        val mAdapter = parent.adapter ?: return
        // 获取第一个可见的item位置
        mFirstVisiblePosition = findFirstVisiblePosition(parent)
        // 获取标签的位置，
        val pinnedHeaderPosition = findPinnedHeaderPosition(mFirstVisiblePosition)
        if (pinnedHeaderPosition >= 0 && mPinnedHeaderPosition != pinnedHeaderPosition) {
            // 标签位置有效并且和缓存的位置不同
            mPinnedHeaderPosition = pinnedHeaderPosition
            // 获取标签的type
            val type = mAdapter.getItemViewType(pinnedHeaderPosition)
            // 手动调用创建标签
            val holder = mAdapter.createViewHolder(parent, type)
            mAdapter.bindViewHolder(holder, mPinnedHeaderPosition)
            // 缓存标签
            mPinnedHeaderView = holder.itemView
            fixLayoutSize(parent)
        }
    }

    private fun fixLayoutSize(parent: ViewGroup) {
        val mPinnedHeaderView = mPinnedHeaderView ?: return
        mPinnedHeaderView.layoutDirection = View.LAYOUT_DIRECTION_LTR
        var lp = mPinnedHeaderView.layoutParams
        if (lp == null) {
            // 标签默认宽度占满parent
            lp = ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT
            )
            mPinnedHeaderView.layoutParams = lp
        }
        // 对高度进行处理
        val heightMode: Int
        var heightSize: Int
        if (lp.height >= 0) {
            heightMode = View.MeasureSpec.EXACTLY
            heightSize = lp.height
        } else if (lp.height == ViewGroup.LayoutParams.MATCH_PARENT) {
            heightMode = View.MeasureSpec.EXACTLY
            heightSize = parent.height
        } else if (lp.height == ViewGroup.LayoutParams.WRAP_CONTENT) {
            heightMode = View.MeasureSpec.AT_MOST
            heightSize = parent.height
        } else {
            heightMode = View.MeasureSpec.AT_MOST
            heightSize = parent.height
        }
        mRecyclerViewPaddingLeft = parent.paddingLeft
        val recyclerViewPaddingRight = parent.paddingRight
        mRecyclerViewPaddingTop = parent.paddingTop
        val recyclerViewPaddingBottom = parent.paddingBottom
        if (lp is MarginLayoutParams) {
            val mlp = lp
            mHeaderLeftMargin = mlp.leftMargin
            mHeaderTopMargin = mlp.topMargin
            mHeaderRightMargin = mlp.rightMargin
            mHeaderBottomMargin = mlp.bottomMargin
        }
        // 最大高度为RecyclerView的高度减去padding
        val maxHeight = parent.height - mRecyclerViewPaddingTop - recyclerViewPaddingBottom
        // 不能超过maxHeight
        heightSize = heightSize.coerceAtMost(maxHeight)

        // 因为标签默认宽度占满parent，所以宽度强制为RecyclerView的宽度减去padding
        val widthSpec = View.MeasureSpec.makeMeasureSpec(
            parent.width - mRecyclerViewPaddingLeft - recyclerViewPaddingRight - mHeaderLeftMargin - mHeaderRightMargin,
            View.MeasureSpec.EXACTLY
        )
        val heightSpec = View.MeasureSpec.makeMeasureSpec(heightSize, heightMode)
        // 强制测量
        mPinnedHeaderView.measure(widthSpec, heightSpec)
        val mLeft = mRecyclerViewPaddingLeft + mHeaderLeftMargin
        val mRight = mPinnedHeaderView.measuredWidth + mLeft
        val mTop = mRecyclerViewPaddingTop + mHeaderTopMargin
        val mBottom = mPinnedHeaderView.measuredHeight + mTop
        // 位置强制布局在顶部
        mPinnedHeaderView.layout(mLeft, mTop, mRight, mBottom)
    }

    /**
     * 从传入位置递减找出标签的位置
     *
     * @param formPosition
     * @return
     */
    private fun findPinnedHeaderPosition(formPosition: Int): Int {
        for (position in formPosition downTo 0) {
            // 位置递减，只要查到位置是标签，立即返回此位置
            if (isPinnedHeader(position)) {
                return position
            }
        }
        return -1
    }

    /**
     * 找出第一个可见的Item的位置
     *
     * @param layoutManager
     * @return
     */
    private fun findFirstVisiblePosition(parent: RecyclerView): Int {
        var firstVisiblePosition = 0
        val layoutManager = parent.layoutManager
        if (layoutManager is GridLayoutManager) {
            firstVisiblePosition = layoutManager.findFirstVisibleItemPosition()
        } else if (layoutManager is LinearLayoutManager) {
            firstVisiblePosition = layoutManager.findFirstVisibleItemPosition()
        } else if (layoutManager is StaggeredGridLayoutManager) {
            val into = IntArray(layoutManager.spanCount)
            layoutManager.findFirstVisibleItemPositions(into)
            firstVisiblePosition = Int.MAX_VALUE
            for (pos in into) {
                firstVisiblePosition = Math.min(pos, firstVisiblePosition)
            }
        }
        return firstVisiblePosition
    }

    /**
     * 检查缓存
     *
     * @param parent
     */
    private fun checkCache(parent: RecyclerView) {
        val adapter = parent.adapter
        if (mAdapter !== adapter) {
            // 适配器为null或者不同，清空缓存
            reset()
            mAdapter = adapter
            mAdapter?.registerAdapterDataObserver(object : AdapterDataObserver() {
                override fun onChanged() {
                    super.onChanged()
                    reset()
                }

                override fun onItemRangeChanged(positionStart: Int, itemCount: Int) {
                    super.onItemRangeChanged(positionStart, itemCount)
                    reset()
                }

                override fun onItemRangeChanged(positionStart: Int, itemCount: Int, payload: Any?) {
                    super.onItemRangeChanged(positionStart, itemCount, payload)
                    reset()
                }

                override fun onItemRangeInserted(positionStart: Int, itemCount: Int) {
                    super.onItemRangeInserted(positionStart, itemCount)
                    reset()
                }

                override fun onItemRangeRemoved(positionStart: Int, itemCount: Int) {
                    super.onItemRangeRemoved(positionStart, itemCount)
                    reset()
                }

                override fun onItemRangeMoved(fromPosition: Int, toPosition: Int, itemCount: Int) {
                    super.onItemRangeMoved(fromPosition, toPosition, itemCount)
                    reset()
                }
            })
        }
    }

    private fun reset() {
        mPinnedHeaderPosition = -1
        mPinnedHeaderView = null
    }
}