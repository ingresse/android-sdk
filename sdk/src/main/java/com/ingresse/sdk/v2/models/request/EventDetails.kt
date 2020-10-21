package com.ingresse.sdk.v2.models.request

import com.ingresse.sdk.v2.defaults.Values.QueryParams.EVENT_DETAILS_IDENTIFY_METHOD

data class EventDetailsById(
    val eventId: String,
    val fields: String?,
) {
    constructor(eventId: String) : this(
        eventId = eventId,
        fields = null
    )
}

data class EventDetailsByLink(
    val link: String,
    val method: String,
    val fields: String?,
) {
    constructor(link: String) : this(
        link = link,
        fields = null,
        method = EVENT_DETAILS_IDENTIFY_METHOD
    )

    constructor(
        link: String,
        fields: String,
    ) : this(
        link = link,
        fields = fields,
        method = EVENT_DETAILS_IDENTIFY_METHOD
    )
}
