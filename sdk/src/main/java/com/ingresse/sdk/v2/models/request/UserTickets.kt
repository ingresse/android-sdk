package com.ingresse.sdk.v2.models.request

import com.ingresse.sdk.v2.defaults.Values.Request.INITIAL_PAGE
import com.ingresse.sdk.v2.defaults.Values.Request.PAGE_SIZE

data class UserTickets(
    val userId: String,
    val userToken: String,
    val eventId: Int?,
    val page: Int?,
    val pageSize: Int?,
) {
    constructor(
        userId: String,
        userToken: String,
    ) : this(
        userId = userId,
        userToken = userToken,
        eventId = null,
        page = INITIAL_PAGE,
        pageSize = PAGE_SIZE
    )

    constructor(
        userId: String,
        userToken: String,
        eventId: Int,
    ) : this(
        userId = userId,
        userToken = userToken,
        eventId = eventId,
        page = INITIAL_PAGE,
        pageSize = PAGE_SIZE
    )
}
