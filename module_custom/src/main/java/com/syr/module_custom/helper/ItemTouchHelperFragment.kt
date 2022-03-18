package com.syr.module_custom.helper

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.ItemTouchHelper
import com.consultation.myapplication.helper.*
import com.syr.hiltdemo.utils.showToast
import com.syr.module_common.base.BaseFragment
import com.syr.module_custom.R
import com.syr.module_custom.databinding.FragmentItemTouchHelperBinding

class ItemTouchHelperFragment : BaseFragment() {
    private lateinit var binding: FragmentItemTouchHelperBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentItemTouchHelperBinding.inflate(inflater, container, false)
        return binding.root
    }

    private val selectedList = mutableListOf(
        ItemBean(1, ServiceType.COMMON, "常用交易", "A"),
        ItemBean(6, ServiceType.FINANCE, "金融服务", "F"),
        ItemBean(9, ServiceType.FINANCE, "金融服务", "I"),
        ItemBean(14, ServiceType.MANAGE, "管理服务", "N"),
        ItemBean(15, ServiceType.MANAGE, "管理服务", "O"),
        ItemBean(21, ServiceType.SECURITY, "保障服务", "U"),
        ItemBean(24, ServiceType.OTHER, "其他", "X")
    )
    private val allList = mutableListOf(
        ItemBean(1, ServiceType.COMMON, "常用交易", "A"),
        ItemBean(2, ServiceType.COMMON, "常用交易", "B"),
        ItemBean(3, ServiceType.COMMON, "常用交易", "C"),
        ItemBean(4, ServiceType.COMMON, "常用交易", "D"),
        ItemBean(5, ServiceType.FINANCE, "金融服务", "E"),
        ItemBean(6, ServiceType.FINANCE, "金融服务", "F"),
        ItemBean(7, ServiceType.FINANCE, "金融服务", "G"),
        ItemBean(8, ServiceType.FINANCE, "金融服务", "H"),
        ItemBean(9, ServiceType.FINANCE, "金融服务", "I"),
        ItemBean(10, ServiceType.FINANCE, "金融服务", "J"),
        ItemBean(11, ServiceType.MANAGE, "管理服务", "K"),
        ItemBean(12, ServiceType.MANAGE, "管理服务", "L"),
        ItemBean(13, ServiceType.MANAGE, "管理服务", "M"),
        ItemBean(14, ServiceType.MANAGE, "管理服务", "N"),
        ItemBean(15, ServiceType.MANAGE, "管理服务", "O"),
        ItemBean(16, ServiceType.MANAGE, "管理服务", "P"),
        ItemBean(17, ServiceType.MANAGE, "管理服务", "Q"),
        ItemBean(18, ServiceType.MANAGE, "管理服务", "R"),
        ItemBean(19, ServiceType.SECURITY, "保障服务", "S"),
        ItemBean(20, ServiceType.SECURITY, "保障服务", "T"),
        ItemBean(21, ServiceType.SECURITY, "保障服务", "U"),
        ItemBean(22, ServiceType.SECURITY, "保障服务", "V"),
        ItemBean(23, ServiceType.SECURITY, "保障服务", "W"),
        ItemBean(24, ServiceType.OTHER, "其他", "X"),
        ItemBean(25, ServiceType.OTHER, "其他", "Y"),
        ItemBean(26, ServiceType.OTHER, "其他", "Z")
    )
    private val selectedAdapter: SelectedAdapter by lazy {
        SelectedAdapter().apply {
            dataList = selectedList
            onItemRemove = { item ->
                if (itemCount == 4) {
                    requireContext().showToast("最少保留4个")
                    false
                } else {
                    mAdapter.unselectedItem(item)
                    true
                }
            }
        }
    }
    private val mAdapter: MAdapter by lazy {
        MAdapter().apply {
            val list = mutableListOf<UiModel>()
            var lastTitleIndex = 0
            allList.forEachIndexed { index, it ->
                it.selected = null != selectedList.find { itemBean -> itemBean.id == it.id }
                if (list.getOrNull(list.lastIndex)?.item?.type != it.type) {
                    list.add(UiModel.SeparatorModel(it.copy().apply { id = -1 }))
                    lastTitleIndex = list.size
                }
                list.add(UiModel.ItemModel(it.apply { typeIndex = lastTitleIndex }))
            }
            dataList = list
            onItemAdd = { position, data ->
                if (selectedAdapter.itemCount == 11) {
                    requireContext().showToast("最多添加11个")
                    false
                } else {
                    selectedAdapter.addItem(data)
                    true
                }
            }
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        binding.recyclerAll.layoutManager = GridLayoutManager(requireActivity(), 4).apply {
            spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
                override fun getSpanSize(position: Int) =
                    if (mAdapter.getItemViewType(position) == R.layout.layout_title) 4 else 1

            }
        }
        binding.recyclerAll.adapter = mAdapter
        binding.recyclerAll.addItemDecoration(HomeItemDecoration())

        binding.recyclerSelected.layoutManager = GridLayoutManager(requireActivity(), 4)
        binding.recyclerSelected.adapter = selectedAdapter
        binding.recyclerSelected.addItemDecoration(HomeItemDecoration())
        val helper = ItemTouchHelper(MItenHelper())
        helper.attachToRecyclerView(binding.recyclerSelected)
    }
}