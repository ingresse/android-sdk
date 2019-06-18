package com.ingresse.sdk.model.request

data class EventAttributes(
    var eventId: String = "",
    var filters: List<String>? = null)