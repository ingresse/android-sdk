package com.ingresse.sdk.base

import com.ingresse.sdk.errors.APIError

interface IngresseCallback<T> {
    fun onSuccess(data: T?)
    fun onError(error: APIError)
    fun onRetrofitError(error: Throwable)
}
