package com.ingresse.sdk.v2.models.request

import com.ingresse.sdk.v2.defaults.INITIAL_PAGE
import com.ingresse.sdk.v2.defaults.PAGE_SIZE

data class HighlightBannerEvents(
    val state: String,
    var page: Int,
    var pageSize: Int
) {
    constructor(state: String) : this(
        state = state,
        page = INITIAL_PAGE,
        pageSize = PAGE_SIZE
    )
}
