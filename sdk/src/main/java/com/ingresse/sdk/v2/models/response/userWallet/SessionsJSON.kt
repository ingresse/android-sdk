package com.ingresse.sdk.v2.models.response.userWallet

data class SessionsJSON(
    val data: List<SessionDataJSON>?
)

data class SessionDataJSON(
    val id: Int?,
    val datetime: String?
)
