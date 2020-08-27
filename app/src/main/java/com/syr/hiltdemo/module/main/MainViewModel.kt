package com.syr.hiltdemo.module.main

import android.widget.Toast
import androidx.databinding.ObservableField
import androidx.hilt.Assisted
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import com.syr.hiltdemo.HiltApp
import com.syr.hiltdemo.R
import com.syr.hiltdemo.base.BaseResp
import com.syr.hiltdemo.base.BaseViewModel
import com.syr.hiltdemo.net.HiltRepository
import com.syr.hiltdemo.net.core.ResponseObserver
import com.syr.hiltdemo.net.vo.IdentityInfo
import com.syr.hiltdemo.net.vo.TimeLineJson
import com.syr.hiltdemo.utils.UiUtil

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
        add(
            repository.userIdentityStatus(mutableMapOf())
                .subscribeWith(object : ResponseObserver<BaseResp<IdentityInfo>>() {
                    override fun onNext(t: BaseResp<IdentityInfo>) {
                        if (t.succeeded) {
                            resultField.set("请求成功：${t.data?.status}")
                        } else {
                            resultField.set("请求成功：${t.msg}")
                        }
                    }

                    override fun onError(e: Throwable) {
                        super.onError(e)
                        resultField.set("请求失败：${getHandledResonseError()}  e=${e}")
                    }
                })
        )
    }

    private fun getJson() {
        add(
            repository.timelineJson()
                .subscribeWith(object : ResponseObserver<TimeLineJson>() {
                    override fun onNext(t: TimeLineJson) {
                        resultField.set("请求成功成功：${t.message}\n${t.documentation_url}")
                    }

                    override fun onError(e: Throwable) {
                        super.onError(e)
                        resultField.set("请求成功失败：${getHandledResonseError()}  e=${e}")
                    }
                })
        )
    }
}