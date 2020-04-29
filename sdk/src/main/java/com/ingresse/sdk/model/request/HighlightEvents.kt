package com.ingresse.sdk.model.request

data class HighlightEvents(
    val state: String = "",
    var page: Int = 0,
    var pageSize: Int = 0
)