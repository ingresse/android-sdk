package com.ingresse.sdk.model.request

data class EventSearch(val title: String,
                       val size: String = "20",
                       val from: String = "now-6h",
                       val orderBy: String = "sessions.dateTime",
                       val offset: String = "0")