package com.ingresse.sdk.v2.models.request

data class EventTickets(
    val eventId: Int,
    val sessionId: String,
) {

    constructor(
        eventId: Int,
    ) : this(
        eventId = eventId,
        sessionId = PASSPORT_SESSION_TYPE
    )

    companion object {
        const val PASSPORT_SESSION_TYPE = "passport"
    }
}
