package com.syr.module_custom

import android.os.Bundle
import android.view.MenuItem
import com.alibaba.android.arouter.facade.annotation.Autowired
import com.alibaba.android.arouter.facade.annotation.Route
import com.google.android.material.tabs.TabLayoutMediator
import com.syr.module_common.base.BaseActivity
import com.syr.module_common.net.core.RouterHub
import com.syr.module_custom.databinding.ActivityCustomBinding

/**
 * 自定义view
 * Created by songyaru on 2020/9/25.
 */
@Route(path = RouterHub.CUSTOM_DETAILSACTIVITY)
class CustomActivity : BaseActivity() {

    private lateinit var binding: ActivityCustomBinding

    @Autowired
    @JvmField
    var buttonText = "toHomeFragment"

    private val pageModels = mutableListOf(
        PageModel(R.layout.sample_color, R.string.title_draw_color, R.layout.practice_color),
        PageModel(R.layout.sample_circle, R.string.title_draw_circle, R.layout.practice_circle),
        PageModel(R.layout.sample_rect, R.string.title_draw_rect, R.layout.practice_rect),
        PageModel(R.layout.sample_point, R.string.title_draw_point, R.layout.practice_point),
        PageModel(R.layout.sample_oval, R.string.title_draw_oval, R.layout.practice_oval),
        PageModel(R.layout.sample_line, R.string.title_draw_line, R.layout.practice_line),
        PageModel(R.layout.sample_round_rect, R.string.title_draw_round_rect, R.layout.practice_round_rect),
        PageModel(R.layout.sample_arc, R.string.title_draw_arc, R.layout.practice_arc),
        PageModel(R.layout.sample_path, R.string.title_draw_path, R.layout.practice_path),
        PageModel(R.layout.sample_histogram, R.string.title_draw_histogram, R.layout.practice_histogram),
        PageModel(R.layout.sample_pie_chart, R.string.title_draw_pie_chart, R.layout.practice_pie_chart),
        PageModel(R.layout.layout_code_input, R.string.title_code_input, R.layout.layout_code_input)
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCustomBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.title = buttonText
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        binding.viewpager.adapter = CommonStatePagerAdapter(this, pageModels)
        TabLayoutMediator(binding.tabLayout, binding.viewpager) { tab, position ->
            tab.text = getString(pageModels[position].titleRes)
        }.attach()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                onBackPressed()
                return false
            }
            else -> return super.onOptionsItemSelected(item)
        }
    }
}