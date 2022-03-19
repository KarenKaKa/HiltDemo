package com.syr.module_custom.addressbook

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.syr.module_common.base.BaseFragment
import com.syr.module_custom.databinding.FragmentItemDecorationBinding

class ItemDecorationFragment : BaseFragment() {
    private lateinit var binding: FragmentItemDecorationBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentItemDecorationBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.flowLayoutRecycler.layoutManager = FlowLayoutManager()
        binding.flowLayoutRecycler.adapter = FlowAdapter().apply {
            dataList = mutableListOf("view", "LinearLayoutManager", "FlowAdapter", "false", "flowLayoutRecycler", "apply","apply","apply")
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

    }
}