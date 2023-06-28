package com.ingresse.sdk.v2.models.request

data class FaceBankLogin(
    var code: String,
    var redirectUri: String,
    val device: LoginDevice,
)
