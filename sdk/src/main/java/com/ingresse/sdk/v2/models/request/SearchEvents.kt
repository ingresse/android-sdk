package com.ingresse.sdk.v2.models.request

import com.ingresse.sdk.v2.defaults.CURRENT_DATE_MINUS_SIX_HOURS
import com.ingresse.sdk.v2.defaults.INGRESSE_COMPANY_NUMBER
import com.ingresse.sdk.v2.defaults.PAGE_SIZE

data class SearchEvents(
    val company: String,
    val title: String?,
    val state: String?,
    val category: String?,
    val term: String?,
    val size: Int,
    val from: String?,
    val to: String?,
    val orderBy: String?,
    val offset: Int
) {
    constructor(
        company: String? = null,
        title: String? = null,
        state: String? = null,
        category: String? = null,
        term: String? = null,
        size: Int? = null,
        from: String? = null,
        to: String? = null,
        orderBy: String? = null,
        offset: Int? = null
    ) : this(
        company = company ?: INGRESSE_COMPANY_NUMBER,
        title = title,
        state = state,
        category = category,
        term = term,
        size = size ?: PAGE_SIZE,
        from = from ?: CURRENT_DATE_MINUS_SIX_HOURS,
        to = to,
        orderBy = orderBy,
        offset = offset ?: 0
    )
}
