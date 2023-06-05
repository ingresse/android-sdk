package com.ingresse.sdk.v2.models.request

import com.google.gson.annotations.SerializedName

data class LoginDevice(
    val id: String,
    val name: String,
    @SerializedName("device_type")
    val deviceType: String,
)
