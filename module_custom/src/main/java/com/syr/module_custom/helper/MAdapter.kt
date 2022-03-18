package com.consultation.myapplication.helper

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.syr.module_custom.R
import com.syr.module_custom.databinding.LayoutTitleBinding
import com.syr.module_custom.databinding.LayoutUnselecteItemBinding

internal class MAdapter : RecyclerView.Adapter<AdapterViewHolder>() {
    var dataList: MutableList<UiModel> = mutableListOf()
        set(value) {
            field = value
            notifyItemRangeChanged(0, field.size)
        }
    var onItemAdd: ((Int, ItemBean) -> Boolean)? = null

    fun unselectedItem(item: ItemBean) {
        for (i in 0 until dataList.size) {
            if (dataList[i].item.id == item.id) {
                dataList[i].item.selected = false
                notifyItemChanged(i)
                break
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        when (viewType) {
            R.layout.layout_unselecte_item -> ItemViewHolder(
                LayoutUnselecteItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            )
            else -> TitleViewHolder(LayoutTitleBinding.inflate(LayoutInflater.from(parent.context), parent, false))
        }

    override fun onBindViewHolder(holder: AdapterViewHolder, position: Int) {
        val data = dataList.getOrNull(position)?.item ?: return
        when (holder) {
            is TitleViewHolder -> {
                holder.titleBinding.title.text = data.typeName
            }
            is ItemViewHolder -> {
                holder.itemBinding.name.text = data.name
                holder.itemBinding.add.visibility = if (data.selected) View.GONE else View.VISIBLE
                holder.itemBinding.add.setOnClickListener {
                    if (onItemAdd?.invoke(position, data) == true) {
                        data.selected = true
                        notifyItemChanged(position)
                    }
                }
            }
        }
    }

    override fun getItemViewType(position: Int) = when (dataList.get(position)) {
        is UiModel.ItemModel -> R.layout.layout_unselecte_item
        is UiModel.SeparatorModel -> R.layout.layout_title
    }

    override fun getItemCount() = dataList.size

    inner class TitleViewHolder(val titleBinding: LayoutTitleBinding) :
        AdapterViewHolder(titleBinding.root) {
    }

    inner class ItemViewHolder(val itemBinding: LayoutUnselecteItemBinding) :
        AdapterViewHolder(itemBinding.root) {

    }
}


open class AdapterViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
sealed class UiModel(open var item: ItemBean) {
    data class ItemModel(override var item: ItemBean) : UiModel(item)
    data class SeparatorModel(override var item: ItemBean) : UiModel(item)
}
