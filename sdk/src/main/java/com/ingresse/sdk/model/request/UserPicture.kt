package com.ingresse.sdk.model.request

data class UserPicture(
    var userId: String,
    var userToken: String,
    var picture: String)