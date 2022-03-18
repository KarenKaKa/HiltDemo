package com.consultation.myapplication.helper

import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView

/**
 * Created by songyaru on 2022/3/1.
 */
internal class MItenHelper : ItemTouchHelper.Callback() {

    override fun getMovementFlags(
        recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder
    ): Int {
        val dragFlags = when (recyclerView.layoutManager) {
            is GridLayoutManager -> {
                ItemTouchHelper.UP or ItemTouchHelper.DOWN or ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT
            }
            else -> {
                ItemTouchHelper.UP or ItemTouchHelper.DOWN
            }
        }
        val swipeFlags = 0
        return makeMovementFlags(dragFlags, swipeFlags)
    }

    override fun onMove(
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder,
        target: RecyclerView.ViewHolder
    ): Boolean {
        // 不同Type之间不可移动
        if (viewHolder.itemViewType != target.itemViewType) {
            return false
        }

        (recyclerView.adapter as? OnItemMoveListener)?.onItemMoved(
            viewHolder.adapterPosition,
            target.adapterPosition
        )

        return true
    }

    override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
    }

    override fun onSelectedChanged(viewHolder: RecyclerView.ViewHolder?, actionState: Int) {
        // 不在闲置状态
        if (actionState != ItemTouchHelper.ACTION_STATE_IDLE) {
            (viewHolder as? OnItemDragListener)?.onItemSelected()
        }
        super.onSelectedChanged(viewHolder, actionState)
    }

    // drag/swip结束
    override fun clearView(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder) {
        (viewHolder as? OnItemDragListener)?.onItemFinish()
        super.clearView(recyclerView, viewHolder)
    }
}