package com.ingresse.sdk.model.request

data class UserChangePassword(
        var userId: String,
        var userToken: String,
        var password: PasswordInfo)

data class PasswordInfo(
        var password: String,
        var newPassword: String)