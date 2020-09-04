package com.syr.hiltdemo.module.main

import android.widget.Toast
import androidx.databinding.ObservableField
import androidx.hilt.Assisted
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import com.syr.hiltdemo.HiltApp
import com.syr.hiltdemo.R
import com.syr.hiltdemo.base.BaseViewModel
import com.syr.hiltdemo.base.ResultData
import com.syr.hiltdemo.net.HiltRepository
import com.syr.hiltdemo.utils.UiUtil
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

/**
 * @author songyaru
 * @date 2020/8/17
 */
class MainViewModel @ViewModelInject constructor(
    private val repository: HiltRepository,
    @Assisted private val savedStateHandle: SavedStateHandle
) : BaseViewModel() {

    val resultField = ObservableField<String>("Hello World!")
    val toHome = MutableLiveData<String>()
    private var inputPhone = ""

    fun afterPhoneChanged(phone: String?) {
        inputPhone = phone ?: ""
    }

    fun startRequest() {
        if (!UiUtil.isAvailable()) {
            Toast.makeText(HiltApp.instance, R.string.net_error, Toast.LENGTH_SHORT).show()
            return
        }

        if (inputPhone.isBlank()) {
            getJson()
        } else {
            userIdentityStatus()
        }
    }

    fun navigation2Home() {
        toHome.value = null
    }

    private fun userIdentityStatus() {
        launchUI {
            val bean = withContext(Dispatchers.IO) { repository.userIdentityStatus(mutableMapOf()) }
            when (bean) {
                is ResultData.Success -> {
                    resultField.set("请求成功：${bean.data?.status}")
                }
                is ResultData.Error -> {
                    resultField.set("请求失败：e=${bean}")
                }
            }
        }
    }

    private fun getJson() {
        launchUI {
            val bean = withContext(Dispatchers.IO) { repository.timelineJson() }
            when (bean) {
                is ResultData.Success -> {
                    resultField.set("请求成功成功：${bean.data.message}\n${bean.data.documentation_url}")
                }
                is ResultData.Error -> {
                    resultField.set("请求失败：e=${bean}")
                }
            }
        }
    }
}