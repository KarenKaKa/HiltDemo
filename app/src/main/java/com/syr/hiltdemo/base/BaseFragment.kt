package com.syr.hiltdemo.base

import android.app.Activity
import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

/**
 * @author songyaru
 * @date 2020/8/21
 */
abstract class BaseFragment : Fragment() {
    lateinit var mContent: Context
    override fun onAttach(context: Context) {
        super.onAttach(context)
        mContent = context
    }

    inline fun <reified VM : ViewModel> viewModels(
        noinline factoryProducer: (() -> ViewModelProvider.Factory)? = null
    ): Lazy<VM> {
        val factoryPromise = factoryProducer ?: {
            defaultViewModelProviderFactory
        }
        return ViewModelLazy(VM::class, { viewModelStore }, factoryPromise)
    }
}