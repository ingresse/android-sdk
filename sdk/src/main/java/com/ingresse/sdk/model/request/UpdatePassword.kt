package com.ingresse.sdk.model.request

data class UpdatePassword(
        var email: String,
        var password: String,
        var hash: String)