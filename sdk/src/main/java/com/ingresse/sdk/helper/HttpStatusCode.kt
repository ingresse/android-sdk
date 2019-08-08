package com.ingresse.sdk.helper

enum class HttpStatusCode(val code: Int, val message: String) {
    OK(200, "OK"),
    UNAUTHORIZED(401, "Unauthorized"),
    TOO_MANY_REQUESTS(429, "Too many requests")
}