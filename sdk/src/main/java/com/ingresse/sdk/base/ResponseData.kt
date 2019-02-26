package com.ingresse.sdk.base

class ResponseData<T> {
    val data: Data<T>? = null
}

class Data<T> {
    val hits: ArrayList<Source<T>> = ArrayList()
    val total: Int? = 0
}

class Source<T> {
    val _source: T? = null
}