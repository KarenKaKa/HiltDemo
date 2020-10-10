package com.syr.module_common.base

/**
 * @author songyaru
 * @date 2020/8/21
 */
data class BaseResp<out R>(
    var code: Int?,
    var msg: String?,
    val data: R?
)