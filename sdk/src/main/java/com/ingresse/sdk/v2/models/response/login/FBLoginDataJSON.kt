package com.ingresse.sdk.v2.models.response.login

data class FBLoginDataJSON(
    val status: Boolean,
    val data: LoginDataJSON?,
    val message: String?,
)
