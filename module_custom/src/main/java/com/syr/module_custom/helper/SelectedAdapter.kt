package com.consultation.myapplication.helper

import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.syr.module_custom.R
import com.syr.module_custom.databinding.LayoutSelectedItemBinding

/**
 * Created by songyaru on 2022/3/1.
 */
internal class SelectedAdapter : RecyclerView.Adapter<SelectedAdapter.DragViewHolder>(),
    OnItemMoveListener {
    var dataList: MutableList<ItemBean> = mutableListOf()
        set(value) {
            field = value
            notifyItemRangeChanged(0, field.size)
        }
    var onItemRemove: ((ItemBean) -> Boolean)? = null

    fun addItem(item: ItemBean) {
        dataList.add(item)
        notifyItemChanged(itemCount)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DragViewHolder {
        return DragViewHolder(LayoutSelectedItemBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: DragViewHolder, position: Int) {
        val data = dataList.getOrNull(position) ?: return
        holder.itemBinding.name.text = data.name

        holder.itemBinding.close.setOnClickListener {
            if (onItemRemove?.invoke(data) == true) {
                dataList.removeAt(holder.adapterPosition)
                notifyItemRemoved(holder.adapterPosition)
            }
        }
    }

    override fun getItemCount() = dataList.size

    override fun onItemMoved(fromPosition: Int, toPosition: Int) {
        val item = dataList.get(fromPosition)
        dataList.removeAt(fromPosition)
        dataList.add(toPosition, item)
        notifyItemMoved(fromPosition, toPosition)
    }

    inner class DragViewHolder(val itemBinding: LayoutSelectedItemBinding) :
        RecyclerView.ViewHolder(itemBinding.root),
        OnItemDragListener {
        override fun onItemSelected() {
            itemView.setBackgroundColor(Color.LTGRAY)
        }

        override fun onItemFinish() {
            itemView.setBackgroundColor(itemView.context.resources.getColor(R.color.cursor_color))
        }
    }
}

interface OnItemMoveListener {
    fun onItemMoved(fromPosition: Int, toPosition: Int)
}

interface OnItemDragListener {
    fun onItemSelected() //选中
    fun onItemFinish() // drag或者move结束
}