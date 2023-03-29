package com.ingresse.sdk.v2.models.request

data class UserData(
    val userId: Int,
    val userToken: String,
    val fields: String? = null
)
