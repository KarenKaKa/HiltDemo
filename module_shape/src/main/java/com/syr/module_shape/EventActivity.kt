package com.syr.module_shape

import android.os.Bundle
import android.util.Log
import android.view.MotionEvent
import androidx.databinding.DataBindingUtil
import com.alibaba.android.arouter.facade.annotation.Route
import com.syr.module_common.base.BaseActivity
import com.syr.module_common.net.core.RouterHub
import com.syr.module_shape.databinding.ActivityEventBinding

/**
 * 事件分发
 * Created by songyaru on 2020/11/12.
 */
@Route(path = RouterHub.SHAPE_EVENTACTIVITY)
class EventActivity : BaseActivity() {
    private lateinit var binding: ActivityEventBinding
    private var activityDis: Boolean? = null //null默认 true  false
    private var activityTou: Boolean? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(
            this, R.layout.activity_event
        )
        binding.activityDisRg.setOnCheckedChangeListener { group, checkedId ->
            activityDis = when (checkedId) {
                R.id.activity_group_rb2 -> true
                R.id.activity_group_rb3 -> false
                else -> null
            }
        }
        binding.activityTouRg.setOnCheckedChangeListener { group, checkedId ->
            activityTou = when (checkedId) {
                R.id.activity_tou_rb2 -> true
                R.id.activity_tou_rb3 -> false
                else -> null
            }
        }
        binding.groupIntRg.setOnCheckedChangeListener { group, checkedId ->
            binding.group.goupInt = when (checkedId) {
                R.id.group_int_rb2 -> true
                R.id.group_int_rb3 -> false
                else -> null
            }
        }
        binding.groupDisRg.setOnCheckedChangeListener { group, checkedId ->
            binding.group.goupDis = when (checkedId) {
                R.id.group_dis_rb2 -> true
                R.id.group_dis_rb3 -> false
                else -> null
            }
        }
        binding.groupTouRg.setOnCheckedChangeListener { group, checkedId ->
            binding.group.goupTou = when (checkedId) {
                R.id.group_tou_rb2 -> true
                R.id.group_tou_rb3 -> false
                else -> null
            }
        }

        binding.viewDisRg.setOnCheckedChangeListener { group, checkedId ->
            binding.view.viewDis = when (checkedId) {
                R.id.view_dis_rb2 -> true
                R.id.view_dis_rb3 -> false
                else -> null
            }
        }
        binding.viewTouRg.setOnCheckedChangeListener { group, checkedId ->
            binding.view.viewTou = when (checkedId) {
                R.id.view_tou_rb2 -> true
                R.id.view_tou_rb3 -> false
                else -> null
            }
        }
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
            Log.e("okhttp", "Activity onTouchEvent event=${event?.action}")
        activityTou?.let { return it }
        return super.onTouchEvent(event)
    }

    override fun dispatchTouchEvent(event: MotionEvent?): Boolean {
            Log.e("okhttp", "Activity dispatchTouchEvent event=${event?.action}")
        activityDis?.let { return it }
        return super.dispatchTouchEvent(event)
    }
}