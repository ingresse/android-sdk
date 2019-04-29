package com.ingresse.sdk.model.request

data class TicketStatus(
        val code: String,
        val sessionId: String,
        val userToken: String)