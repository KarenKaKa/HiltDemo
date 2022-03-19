package com.syr.module_custom.addressbook

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.consultation.myapplication.helper.SectionBean
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

    private val selectionAdapter: SectionAdapter by lazy {
        SectionAdapter().apply {
            dataList = mutableListOf(
                SectionBean("a", "aa"), SectionBean("a", "ab"), SectionBean("a", "ac"),
                SectionBean("b", "ba"), SectionBean("b", "bb"), SectionBean("c", "ccccccccccccc"),
                SectionBean("e", "easd"), SectionBean("e", "eooo"), SectionBean("f", "f"),
                SectionBean("f", "fqwe"), SectionBean("g", "gqq"), SectionBean("g", "guiioo"),
                SectionBean("j", "john"), SectionBean("j", "jily"), SectionBean("s", "sony"),
                SectionBean("s", "sooner"), SectionBean("s", "sun"),
            )
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.flowLayoutRecycler.layoutManager = FlowLayoutManager()
        binding.flowLayoutRecycler.adapter = FlowAdapter().apply {
            dataList = mutableListOf(
                "view", "LinearLayoutManager", "FlowAdapter", "false", "flowLayoutRecycler",
                "apply", "apply", "apply"
            )
        }

        binding.addressBookRecycler.addItemDecoration(PinnedSectionDecoration {
            selectionAdapter.dataList.get(it).groupName
        })
        binding.addressBookRecycler.adapter = selectionAdapter
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

    }
}