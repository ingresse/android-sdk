package com.ingresse.sdk.model.response

import com.ingresse.sdk.base.Array

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
    val sessions: Array<BasketSessionsJSON>?)