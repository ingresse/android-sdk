package com.ingresse.sdk.base

class ResponsePaged<T> {
    var response: ResponseData<T>? = null
}

class ResponseData<T> {
    var data: T? = null
    val paginationInfo: PaginationInfo? = null
}