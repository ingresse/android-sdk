package com.ingresse.sdk.v2.models.request

import com.ingresse.sdk.v2.defaults.Values.QueryParams.HIGHLIGHT_BANNER_METHOD
import com.ingresse.sdk.v2.defaults.Values.Request.INITIAL_PAGE
import com.ingresse.sdk.v2.defaults.Values.Request.PAGE_SIZE

data class HighlightBannerEvents(
    val state: String,
    val method: String,
    val page: Int,
    val pageSize: Int,
) {
    constructor(state: String) : this(
        state = state,
        method = HIGHLIGHT_BANNER_METHOD,
        page = INITIAL_PAGE,
        pageSize = PAGE_SIZE
    )
}
