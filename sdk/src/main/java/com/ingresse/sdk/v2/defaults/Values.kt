package com.ingresse.sdk.v2.defaults

class Values {
    object Company {
        const val INGRESSE = "1"
    }

    object Request {
        const val PAGE_SIZE = 10
        const val INITIAL_PAGE = 1
    }

    object QueryParams {
        const val HIGHLIGHT_BANNER_METHOD = "banner"
        const val CURRENT_DATE_MINUS_SIX_HOURS = "now-6h"
        const val SEARCH_QUERY_ORDER = "sessions.dateTime"
        const val EVENT_DETAILS_IDENTIFY_METHOD = "identify"
    }

    companion object {
        const val TOO_MANY_REQUESTS_ERROR_CODE = 429
        const val UNAUTHORIZED_ERROR_CODE = 401
    }
}
