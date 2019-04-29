package com.ingresse.sdk.model.request

data class EventListByProducer(
    var title: String? = null,
    var size: Int? = 50,
    var orderBy: String? = "sessions.dateTime,desc",
    var from: String? = null,
    var to: String? = null,
    var offset: Int? = 0
)