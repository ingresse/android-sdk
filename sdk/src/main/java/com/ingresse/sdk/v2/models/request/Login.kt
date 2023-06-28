package com.ingresse.sdk.v2.models.request

data class Login(
    val email: String,
    val password: String,
    val device: LoginDevice?,
)
