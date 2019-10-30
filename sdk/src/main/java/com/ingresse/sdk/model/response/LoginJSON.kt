package com.ingresse.sdk.model.response

data class LoginJSON(
        val status: Boolean = false,
        val data: LoginDataJSON?,
        val message: String? = null)

data class LoginDataJSON(
        val token: String = "",
        val userId: String = "",
        val authToken: String = "")