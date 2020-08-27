package com.syr.hiltdemo.net.vo

import com.google.gson.annotations.SerializedName

data class TimeLineJson(
    @field:SerializedName("message")
    val message: String,
    @field:SerializedName("documentation_url")
    val documentation_url: String?
)
