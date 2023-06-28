package com.ingresse.sdk.v2.models.response.login

import com.google.gson.annotations.SerializedName

data class LoginDataJSON(
    val status: Boolean,
    val data: LoginData
) {

    data class LoginData(
        val userId: String,
        val token: String,
        val authToken: String,
        val device: Device?
    ) {

        data class Device(
            val id: String?,
            val name: String?,
            @SerializedName("device_type")
            val deviceType: String?,
            val verified: Boolean?
        )
    }
}
