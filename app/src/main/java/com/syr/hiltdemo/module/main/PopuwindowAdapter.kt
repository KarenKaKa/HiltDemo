package com.syr.hiltdemo.module.main

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.Filter
import android.widget.Filterable
import android.widget.TextView


/**
 * Created by songyaru on 2020/10/20.
 */
class PopuwindowAdapter(private val mContent: Context, val resource: Int, val datas: MutableList<String>) :
    BaseAdapter(), Filterable {
    private val filterLists = mutableListOf<String>()

    init {
        filterLists.addAll(datas)
    }

    override fun getCount() = filterLists.size

    override fun getItem(position: Int) = filterLists[position]

    override fun getItemId(position: Int) = position.toLong()

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val contentView = LayoutInflater.from(mContent).inflate(resource, null) as TextView
        contentView.text = getItem(position)
        return contentView
    }

    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(constraint: CharSequence?): FilterResults {
                val results = FilterResults()
                val filterLists = datas.filter {
                    !constraint.isNullOrBlank() && it.contains(constraint)
                }
                results.count = filterLists.size
                results.values = filterLists
                return results
            }

            override fun publishResults(constraint: CharSequence?, results: FilterResults) {
                filterLists.clear()
                filterLists.addAll(results.values as MutableList<String>)
                notifyDataSetChanged()
            }
        }
    }
}