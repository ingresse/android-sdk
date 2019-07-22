package com.ingresse.sdk.model.response

data class AuthenticationUserDeviceJSON(
        val status: Boolean? = false,
        val data: AuthenticationUserDataJSON? = null)

data class AuthenticationUserDataJSON(
        val token: String? = "",
        val userId: Long? = 0,
        val authToken: String? = "",
        val device: DeviceJSON? = null)

data class DeviceJSON(
        val id: String = "",
        val name: String = "",
        val creationdate: String = "")