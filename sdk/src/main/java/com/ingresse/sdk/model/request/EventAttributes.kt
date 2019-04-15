package com.ingresse.sdk.model.request

data class EventAttributes(
    var eventId: String = "",
    var filters: Array<String>? = null)