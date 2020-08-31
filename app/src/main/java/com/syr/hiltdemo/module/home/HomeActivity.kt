package com.syr.hiltdemo.module.home

import android.content.Intent
import android.os.Bundle
import androidx.navigation.NavController
import com.syr.hiltdemo.R
import com.syr.hiltdemo.base.BaseActivity
import com.syr.hiltdemo.utils.setupWithNavController
import kotlinx.android.synthetic.main.activity_home.*

/**
 * @author songyaru
 * @date 2020/8/21
 */
class HomeActivity : BaseActivity() {
    private var currentNavController: NavController? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        bottom_nav.itemIconTintList = null
//        bottom_nav.setupWithNavController1(findNavController(R.id.nav_host_fragment))
//        supportFragmentManager.findFragmentById(R.id.nav_host_container)?.findNavController()
//            ?.let { bottom_nav.setupWithNavController(it) }

        val navGraphIds = listOf(
            R.navigation.home,
            R.navigation.sort,
            R.navigation.find,
            R.navigation.cart,
            R.navigation.mine
        )
        currentNavController = bottom_nav.setupWithNavController(
            navGraphIds = navGraphIds,
            fragmentManager = supportFragmentManager,
            containerId = R.id.nav_host_container
        ).value
    }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        when (intent?.getStringExtra("toWhat")) {
            "sort" -> {
                bottom_nav.selectedItemId = bottom_nav.menu.getItem(1).itemId
            }
            "find" -> {
                bottom_nav.selectedItemId = bottom_nav.menu.getItem(2).itemId
            }
            "cart" -> {
                bottom_nav.selectedItemId = bottom_nav.menu.getItem(3).itemId
            }
            "mine" -> {
                bottom_nav.selectedItemId = bottom_nav.menu.getItem(4).itemId
            }
            else -> {
//                currentNavController?.navigate(R.id.action_show_home)
                bottom_nav.selectedItemId = bottom_nav.menu.getItem(0).itemId
            }
        }
    }
}