package com.ingresse.sdk.model.request

data class GuestList(
    val eventId: String,
    val sessionId: String,
    val from: Long?,
    val page: Int,
    val pageSize: Int,
    val userToken: String)