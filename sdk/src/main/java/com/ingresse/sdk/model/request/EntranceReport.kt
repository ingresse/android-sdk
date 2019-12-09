package com.ingresse.sdk.model.request

data class EntranceReport(
    var eventId: String = "",
    var sessionId: String = "",
    var itemId: String? = null)