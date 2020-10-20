package com.ingresse.sdk.v2.models.base

data class Data<T>(
    val hits: List<Source<T>>,
    val total: Int
)
