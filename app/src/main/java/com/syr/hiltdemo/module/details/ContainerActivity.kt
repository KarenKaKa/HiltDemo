package com.syr.hiltdemo.module.details

import android.content.Context
import android.content.Intent
import android.os.Bundle
import com.syr.hiltdemo.R
import com.syr.hiltdemo.module.home.HomeFragment
import com.syr.module_common.base.BaseActivity
import com.syr.module_common.utils.replaceFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
internal class ContainerActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_container)
        val from = intent.getStringExtra(FROM)
        when (from) {
            SourceFrom.ARTICLES -> replaceFragment(R.id.fragment_container, ArticlesFragment.newInstance())
            SourceFrom.DETAILS -> replaceFragment(R.id.fragment_container, HomeFragment())
            else -> finish()
        }
    }

    companion object {
        fun launchActivity(context: Context, @SourceFrom from: String? = null) {
            val intent = Intent(context, ContainerActivity::class.java)
            intent.putExtra(FROM, from)
            context.startActivity(intent)
        }

        private const val FROM = "sourceFrom"
    }
}