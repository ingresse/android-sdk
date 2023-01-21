package com.ingresse.sdk.request

data class AuthenticationUserDevice(
        var userToken: String,
        var challenge: String?,
        var code: String? = null,
        var device: String? = null)