package com.ingresse.sdk.base

class ResponseHits<T> {
    val data: Data<T>? = null
    val code: Int? = null
    val message: String? = null
}

class Data<T> {
    val hits: ArrayList<Source<T>> = ArrayList()
    val total: Int = 0
}

class Source<T> {
    val _source: T? = null
}