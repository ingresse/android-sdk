package com.ingresse.sdk.v2.models.request

data class RequestReset(val email: String)

data class ValidateHash(
    var email: String,
    var hash: String,
)

data class UpdatePassword(
    var email: String,
    var password: String,
    var hash: String,
)
