package com.ingresse.sdk.model.request

data class EventListByProducer(
    var title: String? = null,
    var size: Int? = null,
    var orderBy: String? = "sessions.dateTime",
    var from: String? = null,
    var to: String? = null,
    var offset: Int? = null
)