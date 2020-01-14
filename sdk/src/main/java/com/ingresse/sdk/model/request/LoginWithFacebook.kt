package com.ingresse.sdk.model.request

data class LoginWithFacebook(
        var email: String = "",
        var fbToken: String = "",
        var fbUserId: String = "")