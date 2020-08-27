package com.syr.hiltdemo.utils

import androidx.core.view.forEachIndexed
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.syr.hiltdemo.R

/**
 * @author songyaru
 * @date 2020/8/27
 */
fun BottomNavigationView.setupWithNavController(
    navGraphIds: List<Int>,
    fragmentManager: FragmentManager,
    containerId: Int
): LiveData<NavController> {
    // Result. Mutable live data with the selected controlled
    val selectedNavController = MutableLiveData<NavController>()
    if (navGraphIds.isEmpty()) return selectedNavController
    var selectedItemTag = getFragmentTag(selectedItemId)

    fragmentManager.beginTransaction()
        .add(containerId, NavHostFragment.create(navGraphIds[0]), selectedItemTag)
        .commitNowAllowingStateLoss()
    setOnNavigationItemSelectedListener { item ->
        if (fragmentManager.isStateSaved) {
            false
        } else {
            val newlySelectedTag = getFragmentTag(item.itemId)
            if (selectedItemTag != newlySelectedTag) {
                val selectedFragment =
                    fragmentManager.findFragmentByTag(selectedItemTag) as NavHostFragment?
                val fragmentTransaction = fragmentManager.beginTransaction()
                selectedFragment?.let { fragmentTransaction.hide(it) }

                var newlySelectedFragment =
                    fragmentManager.findFragmentByTag(newlySelectedTag) as NavHostFragment?
                if (null == newlySelectedFragment) {
                    var index = 0
                    menu.forEachIndexed { index1, item1 ->
                        if (item1 == item) {
                            index = index1
                            return@forEachIndexed
                        }
                    }
                    newlySelectedFragment = NavHostFragment.create(navGraphIds[index])
                    fragmentTransaction
                        .setCustomAnimations(
                            R.anim.nav_default_enter_anim,
                            R.anim.nav_default_exit_anim,
                            R.anim.nav_default_pop_enter_anim,
                            R.anim.nav_default_pop_exit_anim
                        )
                        .add(containerId, newlySelectedFragment!!, newlySelectedTag)
                        .commitNowAllowingStateLoss()
                } else {
                    fragmentTransaction.show(newlySelectedFragment).commitNowAllowingStateLoss()
                }
                selectedItemTag = newlySelectedTag
                selectedNavController.value = selectedFragment?.navController
                true
            } else {
                false
            }
        }
    }
    return selectedNavController
}

private fun getFragmentTag(index: Int) = "bottomNavigation#$index"


