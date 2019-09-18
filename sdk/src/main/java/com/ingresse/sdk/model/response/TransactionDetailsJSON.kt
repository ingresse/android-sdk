package com.ingresse.sdk.model.response

data class TransactionDetailsJSON(
        val status: String? = "",
        val basket: TransactionBasketJSON?)

data class TransactionBasketJSON(
        val tickets: List<TransactionTicketJSON>?)

data class TransactionTicketJSON(
        val id: Int? = 0,
        val code: String? = "",
        val name: String? = "",
        val checked: Boolean? = false,
        val lastUpdate: Int? = 0,
        val transferred: Boolean? = false,
        val ticket: String? = "",
        val type: String? = "",
        val ticketId: Int? = 0,
        val typeId: Int? = 0,
        val price: String? = "",
        val tax: String? = "",
        val percentTax: String? = "",
        val sessions: List<BasketSessionsJSON>?)

data class BasketSessionsJSON(
        val id: Int? = 0,
        val dateTime: SessionDateJSON?)

data class RefundJSON(
        val operator: String? = "",
        val reason: String? = "",
        val date: String? = "")