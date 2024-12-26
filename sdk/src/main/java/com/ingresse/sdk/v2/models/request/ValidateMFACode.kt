package com.ingresse.sdk.v2.models.request

data class ValidateMFACode(
    val userToken: String,
    val otpCode: String,
)