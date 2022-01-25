package com.syr.hiltdemo.module.details

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.syr.hiltdemo.R
import com.syr.hiltdemo.module.main.MainViewModel
import com.syr.module_common.base.BaseFragment
import com.syr.module_common.common.ArticlesResp
import com.syr.module_common.utils.AdapterViewHolder
import com.syr.module_common.utils.inflate
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_articles.*
import kotlinx.android.synthetic.main.layout_item.view.*

@AndroidEntryPoint
internal class ArticlesFragment : BaseFragment() {

    private val viewModel by viewModels<MainViewModel>()
    private val articlesAdapter: ArticlesAdapter by lazy { ArticlesAdapter() }


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_articles, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        recyclerView.apply {
            adapter = articlesAdapter
        }

        viewModel.getArticles()
        viewModel.articles.observe(viewLifecycleOwner, {
            articlesAdapter.dataList = it
            articlesAdapter.notifyDataSetChanged()
        })
    }

    companion object {
        fun newInstance() = ArticlesFragment()
    }
}

internal class ArticlesAdapter(
    var dataList: List<ArticlesResp.Data.Article?> = listOf()
) : RecyclerView.Adapter<AdapterViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AdapterViewHolder =
        AdapterViewHolder(parent.inflate(R.layout.layout_item))

    override fun onBindViewHolder(holder: AdapterViewHolder, position: Int) {
        val data = dataList[position] ?: return
        holder.itemView.apply {
            title.text = data.title
            date.text = "${data.shareUser} ${data.niceDate}"
        }

    }

    override fun getItemCount() = dataList.size.coerceAtLeast(0)

}