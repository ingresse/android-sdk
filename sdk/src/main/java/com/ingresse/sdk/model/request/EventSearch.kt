package com.ingresse.sdk.model.request

data class EventSearch(var title: String? = null,
                       var category: String? = null,
                       var state: String? = null,
                       var term: String? = null,
                       var size: Int = 20,
                       var from: String? = "now-6h",
                       var to: String? = null,
                       var orderBy: String = "sessions.dateTime",
                       var offset: Int = 0)