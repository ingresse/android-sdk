package com.ingresse.sdk.model.response

class SearchResponse<T> {
    val data: T? = null
    val code: Int? = null
    val message: String? = null
}

data class EventSearchJSON(val hits: Array<SearchSource>,
                           val total: Int)

data class SearchSource(val _source: EventJSON)