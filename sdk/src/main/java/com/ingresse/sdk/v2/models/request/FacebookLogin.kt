package com.ingresse.sdk.v2.models.request

data class FacebookLogin(
    var email: String,
    var facebookToken: String,
    var facebookUserId: String,
)
