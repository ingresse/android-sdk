package com.ingresse.sdk.request

data class AuthenticationUserDevice(
        var userToken: String,
        var code: String? = null,
        val device: String)