package com.ingresse.sdk.v2.models.request

data class EntranceReport(
    val eventId: String,
    val sessionId: String,
    val groupId: String?,
)
