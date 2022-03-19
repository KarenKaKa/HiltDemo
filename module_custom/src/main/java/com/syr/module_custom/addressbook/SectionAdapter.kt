package com.syr.module_custom.addressbook

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.consultation.myapplication.helper.SectionBean
import com.syr.module_common.utils.AdapterViewHolder
import com.syr.module_custom.databinding.LayoutItemSectionBinding

/**
 * Created by songyaru on 2022/3/19.
 */
internal class SectionAdapter : RecyclerView.Adapter<SectionAdapter.ItemViewHolder>() {
    var dataList: MutableList<SectionBean> = mutableListOf()
        set(value) {
            field = value
            notifyItemRangeChanged(0, field.size)
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        ItemViewHolder(LayoutItemSectionBinding.inflate(LayoutInflater.from(parent.context), parent, false))

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val data = dataList.getOrNull(position) ?: return
        holder.itemBinding.name.text = data.name
    }

    override fun getItemCount() = dataList.size


    inner class ItemViewHolder(val itemBinding: LayoutItemSectionBinding) : AdapterViewHolder(itemBinding.root)
}