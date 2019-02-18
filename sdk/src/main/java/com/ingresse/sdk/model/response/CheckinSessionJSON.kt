package com.ingresse.sdk.model.response

data class CheckinSessionJSON(
    val session: SessionJSON?,
    val owner: UserDataJSON?,
    val lastStatus: CheckinStatusJSON?
)