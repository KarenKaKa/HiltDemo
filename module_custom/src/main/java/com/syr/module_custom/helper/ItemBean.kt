package com.consultation.myapplication.helper

import androidx.annotation.StringDef
import java.io.Serializable

/**
 * Created by songyaru on 2022/3/2.
 */
data class ItemBean(
    var id: Int,
    @ServiceType val type: String,
    val typeName: String,
    val name: String,
    var selected: Boolean = false,
    var typeIndex: Int = 0
) : Serializable {
    override fun toString(): String {
        return "id=${id} name=${name} type=${type} typeIndex=${typeIndex}"
    }
}

data class SectionBean(
    var groupName: String,
    var name: String
) : Serializable

@StringDef(
    ServiceType.COMMON,
    ServiceType.FINANCE,
    ServiceType.MANAGE,
    ServiceType.SECURITY,
    ServiceType.OTHER
)
@Retention(AnnotationRetention.SOURCE)
annotation class ServiceType {
    companion object {
        const val COMMON = "1" //常用交易
        const val FINANCE = "2" //金融服务
        const val MANAGE = "3" //管理服务
        const val SECURITY = "4" //保障服务
        const val OTHER = "5" //其他
    }
}