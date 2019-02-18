package com.ingresse.sdk.model.response

data class CheckinStatusJSON(
    val id: Int? = 0,
    val timestamp: String? = "",
    val operation: String? = "",
    val operator: UserDataJSON?,
    val holder: UserDataJSON?)