package com.ingresse.sdk.model.response

data class TransactionSalesGroupJSON(
    val id: Int? = 0,
    val name: String? = "",
    val team: Array<TransactionTeamJSON>?)

data class TransactionTeamJSON(
    val id: Int? = 0,
    val name: String? = "",
    val email: String? = "",
    val manager: Boolean? = false,
    val accountMode: Boolean? = false)