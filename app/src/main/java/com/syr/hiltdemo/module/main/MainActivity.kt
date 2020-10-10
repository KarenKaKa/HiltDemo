package com.syr.hiltdemo.module.main

import android.content.Intent
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import com.syr.hiltdemo.R
import com.syr.hiltdemo.databinding.ActivityMainBinding
import com.syr.hiltdemo.module.home.HomeActivity
import com.syr.module_common.base.BaseActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : BaseActivity() {
    private val viewModel by viewModels<MainViewModel>()
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(
            this, R.layout.activity_main
        )
        binding.viewModel = viewModel
        viewModel.toHome.observe(this, Observer { string ->
            startActivity(Intent(this, HomeActivity::class.java))
        })
    }
}
