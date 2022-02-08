package com.syr.hiltdemo.module.home

import android.content.Intent
import android.os.Bundle
import androidx.navigation.NavController
import com.syr.hiltdemo.R
import com.syr.hiltdemo.databinding.ActivityHomeBinding
import com.syr.hiltdemo.utils.setupWithNavController
import com.syr.module_common.base.BaseActivity

class HomeActivity : BaseActivity() {
    private var currentNavController: NavController? = null
    private lateinit var binding: ActivityHomeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.bottomNav.itemIconTintList = null
//        binding.bottomNav.setupWithNavController1(findNavController(R.id.nav_host_fragment))
//        supportFragmentManager.findFragmentById(R.id.nav_host_container)?.findNavController()
//            ?.let { binding.bottomNav.setupWithNavController(it) }

        val navGraphIds = listOf(
            R.navigation.home,
            R.navigation.sort,
            R.navigation.find,
            R.navigation.cart,
            R.navigation.mine
        )
        currentNavController = binding.bottomNav.setupWithNavController(
            navGraphIds = navGraphIds,
            fragmentManager = supportFragmentManager,
            containerId = R.id.nav_host_container
        ).value
    }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        when (intent?.getStringExtra("toWhat")) {
            "sort" -> {
                binding.bottomNav.selectedItemId = binding.bottomNav.menu.getItem(1).itemId
            }
            "find" -> {
                binding.bottomNav.selectedItemId = binding.bottomNav.menu.getItem(2).itemId
            }
            "cart" -> {
                binding.bottomNav.selectedItemId = binding.bottomNav.menu.getItem(3).itemId
            }
            "mine" -> {
                binding.bottomNav.selectedItemId = binding.bottomNav.menu.getItem(4).itemId
            }
            else -> {
//                currentNavController?.navigate(R.id.action_show_home)
                binding.bottomNav.selectedItemId = binding.bottomNav.menu.getItem(0).itemId
            }
        }
    }
}