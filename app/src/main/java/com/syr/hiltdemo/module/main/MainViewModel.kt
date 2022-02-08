package com.syr.hiltdemo.module.main

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AutoCompleteTextView
import android.widget.PopupWindow
import android.widget.Toast
import androidx.databinding.ObservableField
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.syr.hiltdemo.HiltApp
import com.syr.hiltdemo.R
import com.syr.hiltdemo.net.HiltRepository
import com.syr.hiltdemo.utils.showToast
import com.syr.module_common.base.BaseViewModel
import com.syr.module_common.base.ResultData
import com.syr.module_common.common.ArticlesResp
import com.syr.module_common.common.getCommonRepository
import com.syr.module_common.utils.UiUtil
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.SharingStarted.Companion.WhileSubscribed
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val repository: HiltRepository,
    private val savedStateHandle: SavedStateHandle
) : BaseViewModel() {
    // 跨module获取repository
    private val commonRepository by lazy { HiltApp.instance.getCommonRepository() }
    val articles = MutableLiveData<List<ArticlesResp.Data.Article?>?>()

//    val resultField = ObservableField("Hello World!")
    val toHome = MutableLiveData<String?>()
    private var inputPhone = ""

    fun afterPhoneChanged(phone: String?) {
        inputPhone = phone ?: ""
    }

    fun startRequest() {
        if (!UiUtil.isAvailable(HiltApp.instance)) {
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

    val articlesFlow = commonRepository.getArticlesFlow.stateIn(
        scope = viewModelScope,
        started = WhileSubscribed(5000),
        initialValue = null
    )

    fun getArticles() {
        launchUI {
            val bean = withContext(Dispatchers.IO) { commonRepository.getArticles() }
            when (bean) {
                is ResultData.Success -> {
                    articles.value = bean.data.data?.datas
                }
                is ResultData.Error -> {
                    Log.e("okhttp", "请求失败：e=${bean.exception}")
                    HiltApp.instance.showToast("请求失败：e=${bean.exception}")
                }
            }
        }
    }

    private fun userIdentityStatus() {
        launchUI {
            when (val bean =
                withContext(Dispatchers.IO) { repository.userIdentityStatus(mutableMapOf()) }) {
                is ResultData.Success -> {
//                    resultField.set("请求成功：${bean.data?.status}")
                }
                is ResultData.Error -> {
//                    resultField.set("请求失败：e=${bean.exception}")
                }
            }

            val bean1 =
                withContext(Dispatchers.IO) { repository.userIdentityStatus1(mutableMapOf()) }
            when (bean1) {
                is ResultData.Success -> {
//                    resultField.set("请求成功：${bean1.data.status}")
                }
                is ResultData.ErrorMessage -> {
//                    resultField.set("请求失败：e=${bean1.errorMessage}")
                }
            }
        }


    }

    private fun getJson() {
        launchUI {
            val bean = withContext(Dispatchers.IO) { repository.timelineJson() }
            when (bean) {
                is ResultData.Success -> {
//                    resultField.set("请求成功成功：${bean.data.message}\n${bean.data.documentation_url}")
                }
                is ResultData.Error -> {
//                    resultField.set("请求失败：e=${bean.exception}")
                }
            }
        }
    }

    private var popupWindow: PopupWindow? = null
    private val names = mutableListOf("c北京", "c上海", "c四川", "c河南", "c天津", "c黑龙江")
    fun showPopu(anchor: View) {
        if (popupWindow != null && popupWindow?.isShowing == true) popupWindow?.dismiss()
        popupWindow = null
        val contentView = LayoutInflater.from(anchor.context).inflate(R.layout.layout_popuwindow, null)
        val tvName = contentView.findViewById<AutoCompleteTextView>(R.id.tvName)
        tvName.setAdapter(PopuwindowAdapter(anchor.context, android.R.layout.simple_spinner_dropdown_item, names))
        tvName.threshold = 1
        popupWindow = PopupWindow(contentView, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
        popupWindow?.setBackgroundDrawable(anchor.context.resources.getDrawable(android.R.color.transparent))
        popupWindow?.isOutsideTouchable = true
        popupWindow?.isFocusable = true
        popupWindow?.showAsDropDown(anchor)

    }
}