package com.ingresse.sdk.model.response

data class UserHistoryDataJSON(
        var id: Int,
        var email: String,
        var username: String?,
        var name: String,
        var phone: String,
        var picture: String?)