package com.ingresse.sdk.model.request

data class AuthenticationUserDevice(
    var userToken: String,
    var code: String? = null,
    val uuid: String)