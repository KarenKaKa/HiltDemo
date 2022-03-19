package com.syr.module_custom.addressbook

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.syr.module_common.utils.AdapterViewHolder
import com.syr.module_custom.databinding.LayoutItemFlowBinding

/**
 * Created by songyaru on 2022/3/19.
 */
internal class FlowAdapter : RecyclerView.Adapter<FlowAdapter.ItemViewHolder>() {
    var dataList: MutableList<String> = mutableListOf()
        set(value) {
            field = value
            notifyItemRangeChanged(0, field.size)
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        ItemViewHolder(LayoutItemFlowBinding.inflate(LayoutInflater.from(parent.context), parent, false))

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val data = dataList.getOrNull(position) ?: return
        holder.itemBinding.flowTitle.text = data
    }

    override fun getItemCount() = dataList.size


    inner class ItemViewHolder(val itemBinding: LayoutItemFlowBinding) : AdapterViewHolder(itemBinding.root)
}