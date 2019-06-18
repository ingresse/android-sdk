package com.ingresse.sdk.model.request

data class Transactions(
        var userToken: String,
        var eventId: String? = null,
        var term: String? = null,
        var from: String? = null,
        var to: String? = null,
        var acquirer: String? = null,
        var nsu: String? = null,
        var amount: Int? = null,
        var cardFirstDigits: Int? = null,
        var cardLastDigits: Int? = null,
        var status: String? = null,
        var page: Int? = null,
        var pageSize: Int? = null,
        var order: String? = null)