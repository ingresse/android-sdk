package com.ingresse.sdk.base

class ResponsePaged<T> {
    var responseData: ResponseData<T>? = null
}

class ResponseData<T> {
    var data: ArrayList<T> = ArrayList()
    val paginationInfo: PaginationInfo? = null
}