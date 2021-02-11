package com.ingresse.sdk.v2.models.base

data class IngresseError(
    var responseData: String,
    var responseError: Error,
    var responseStatus: Int,
)
