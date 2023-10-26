package com.ingresse.sdk.model.request

data class CashClosing(val operator: String,
                       val eventId: String? = null,
                       val from: String,
                       val to: String,
                       val userToken: String)