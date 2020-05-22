package com.ingresse.sdk.model.request

data class HighlightEvents(
    val state: String = "",
    val method: HighlightMethod? = null,
    var page: Int = 0,
    var pageSize: Int = 0
)