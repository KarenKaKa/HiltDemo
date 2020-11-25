package com.syr.hiltdemo.module.home

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.syr.hiltdemo.R
import com.syr.hiltdemo.module.details.DetailsActivity
import com.syr.module_common.base.BaseFragment
import com.syr.module_common.net.core.RouterHub
import com.syr.module_common.net.core.Utils
import kotlinx.android.synthetic.main.fragment_home.*

/**
 * @author songyaru
 * @date 2020/8/21
 */
class HomeFragment : BaseFragment() {
//    private val viewModel by viewModels<MainViewModel>()
//    private lateinit var binding: ActivityMainBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
//        binding = DataBindingUtil.inflate(inflater, R.layout.activity_main, container, false)
//        return binding.root
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val pageName = arguments?.getString("pageName")
        val from = arguments?.getString("from") ?: ""

        Log.e("okhttp", "pageName=$pageName from=$from")

        if ("mineFragment" == pageName) {
            text.text = "$pageName\nclick to 首页"
            text.setOnClickListener {
                startActivity(Intent(activity, HomeActivity::class.java).apply {
                    putExtra("from", "from mineFragment")
                })
//                val bundle = bundleOf("from" to "from mineFragment")
//                text.findNavController().navigate(R.id.action_show_home, bundle)
//                findNavController().navigate(R.id.action_show_home)
//                Navigation.findNavController(text).navigate(R.id.action_show_home)
            }
        } else if ("findFragment" == pageName) {
            text.text = "$pageName\nclick to 详情页"
            text.setOnClickListener { startActivity(Intent(activity, DetailsActivity::class.java)) }
        } else if ("homeFragment" == pageName) {
            text.text = "$pageName\nclick to 自定义view"
            text.setOnClickListener {
//                startActivity(Intent(activity, CustomActivity::class.java))
                Utils.navigation(RouterHub.CUSTOM_DETAILSACTIVITY, context = mContent, args = Bundle().apply {
                    putString("buttonText", "去首页")
                })
            }

        } else if ("sortFragment" == pageName) {
            text.text = "$pageName\nclick to 自定义阴影"
            text.setOnClickListener {
                Utils.navigation(RouterHub.SHAPE_SHAPEACTIVITY, context = mContent)
            }
        } else if ("cartFragment" == pageName) {
            text.text = "$pageName\nclick to 事件传递"
            text.setOnClickListener {
                Utils.navigation(RouterHub.SHAPE_EVENTACTIVITY, context = mContent)
            }
        } else {
            text.text = "$from ${pageName ?: "This is Default HomeFragment"}"
        }
    }
}