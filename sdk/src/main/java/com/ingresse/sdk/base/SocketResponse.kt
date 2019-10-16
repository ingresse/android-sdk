package com.ingresse.sdk.base

class SocketResponse<T> {
    val event: String? = null
    val payload: SocketPayload<T>? = null
    val ref: String? = null
    val topic: String? = null
}

class SocketPayload<T> {
    val data: T? = null
    val timestamp: String? = null
    val response: PayloadResponse? = null
    val status: String? = null
}

class PayloadResponse {
    val reason: String? = null
}