package com.ingresse.sdk.model.request

data class ValidateHash(
        var email: String,
        var hash: String)