package com.ingresse.sdk.model.request

data class EventDetailsRequest(
    val eventId: String = "",
    val fields: String? = null
)