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

    object User {
        private const val ID = "id"
        private const val NAME = "name"
        private const val LAST_NAME = "lastname"
        private const val DOCUMENT = "document"
        private const val EMAIL = "email"
        private const val ZIP = "zip"
        private const val NUMBER = "number"
        private const val COMPLEMENT = "complement"
        private const val CITY = "city"
        private const val STATE = "state"
        private const val STREET = "street"
        private const val DISTRICT = "district"
        private const val DDI = "ddi"
        private const val PHONE = "phone"
        private const val VERIFIED = "verified"
        private const val FB_USER_ID = "fbUserId"
        private const val TYPE = "type"
        private const val PICTURES = "pictures"
        private const val PICTURE = "picture"
        private const val PLANNER = "planner"

        val backstageDefaultFields = listOf(
            ID,
            NAME,
            LAST_NAME,
            EMAIL,
            PICTURE,
            PICTURES,
            PLANNER,
            TYPE
        ).joinToString(",")

        val consumerDefaultFields = listOf(
            ID,
            NAME,
            LAST_NAME,
            DOCUMENT,
            EMAIL,
            ZIP,
            NUMBER,
            COMPLEMENT,
            CITY,
            STATE,
            STREET,
            DISTRICT,
            DDI,
            PHONE,
            VERIFIED,
            FB_USER_ID,
            PICTURE,
            PICTURES
        ).joinToString(",")
    }

    companion object {
        const val TOO_MANY_REQUESTS_ERROR_CODE = 429
        const val UNAUTHORIZED_ERROR_CODE = 401
    }
}
