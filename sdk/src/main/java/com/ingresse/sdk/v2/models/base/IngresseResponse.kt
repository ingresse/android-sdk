package com.ingresse.sdk.v2.models.base

data class IngresseResponse<T>(
    var responseData: T?,
    var responseError: Error,
    var responseStatus: Int
)
