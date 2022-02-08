package com.syr.hiltdemo.module.details

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.syr.hiltdemo.databinding.FragmentArticlesBinding
import com.syr.hiltdemo.databinding.LayoutItemBinding
import com.syr.hiltdemo.module.main.MainViewModel
import com.syr.module_common.base.BaseFragment
import com.syr.module_common.common.ArticlesResp
import com.syr.module_common.utils.AdapterViewHolder
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
internal class ArticlesFragment : BaseFragment() {

    private lateinit var binding: FragmentArticlesBinding

    private val viewModel by viewModels<MainViewModel>()
    private val articlesAdapter: ArticlesAdapter by lazy { ArticlesAdapter() }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentArticlesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        binding.recyclerView.apply {
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
    var dataList: List<ArticlesResp.Data.Article?>? = listOf()
) : RecyclerView.Adapter<AdapterViewHolder>() {
    private lateinit var binding: LayoutItemBinding

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AdapterViewHolder {
        binding = LayoutItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return AdapterViewHolder(binding.root)
    }

    override fun onBindViewHolder(holder: AdapterViewHolder, position: Int) {
        val data = dataList?.get(position) ?: return
        holder.itemView.apply {
            binding.title.text = data.title
            binding.date.text = "${data.shareUser} ${data.niceDate}"
        }
    }

    override fun getItemCount() = dataList?.size?.coerceAtLeast(0) ?: 0

}