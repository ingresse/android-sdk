package com.ingresse.sdk.errors

import retrofit2.Response

data class IngresseThrowable(
    var request: String? = "",
    var response: String? = "",
    var reason: String? = ""
): Throwable() {

    override fun toString(): String {
        return "request: ${request.orEmpty()} | response: ${response.orEmpty()} | reason: ${reason.orEmpty()}"
    }

    constructor(response: Response<String>, reason: String? = "") : this() {
        request = response.raw().request().toString()
        this.response = response.body()
        this.reason = reason
    }

    constructor(response: String, reason: String = "") : this() {
        this.response = response
        this.reason = reason
    }
}