package com.ingresse.sdk.v2.models.request

data class ProducerEventDetails(
    val eventId: Int,
    val fields: String?,
) {

    constructor(eventId: Int) : this(
        eventId = eventId,
        fields = null
    )
}
