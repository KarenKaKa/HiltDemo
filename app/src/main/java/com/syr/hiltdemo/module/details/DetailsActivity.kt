package com.syr.hiltdemo.module.details

import android.content.Intent
import android.os.Bundle
import com.syr.hiltdemo.databinding.ActivityDetailsBinding
import com.syr.hiltdemo.module.home.HomeActivity
import com.syr.module_common.base.BaseActivity

/**
 * @author songyaru
 * @date 2020/8/26
 */
class DetailsActivity : BaseActivity() {
    private lateinit var binding: ActivityDetailsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_details)
        binding = ActivityDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.toHomeFragment.setOnClickListener {
            startActivity(
                Intent(this, HomeActivity::class.java).apply { putExtra("toWhat", "home") })
        }
        binding.toSortFragment.setOnClickListener {
            startActivity(
                Intent(this, HomeActivity::class.java).apply { putExtra("toWhat", "sort") })
        }
    }
}