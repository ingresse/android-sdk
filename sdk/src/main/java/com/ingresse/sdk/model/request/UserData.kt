package com.ingresse.sdk.model.request

data class UserData(
    val userId: Int,
    val userToken: String,
    val fields: String? = null)