package com.syr.hiltdemo.module.details

import android.content.Intent
import android.os.Bundle
import com.syr.hiltdemo.R
import com.syr.hiltdemo.base.BaseActivity
import com.syr.hiltdemo.module.home.HomeActivity
import kotlinx.android.synthetic.main.activity_details.*

/**
 * @author songyaru
 * @date 2020/8/26
 */
class DetailsActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_details)

        toHomeFragment.setOnClickListener {
            startActivity(
                Intent(this, HomeActivity::class.java).apply { putExtra("toWhat", "home") })
        }
        toProductFragment.setOnClickListener {
            startActivity(
                Intent(this, HomeActivity::class.java).apply { putExtra("toWhat", "product") })
        }
    }
}