package com.ingresse.sdk.model.request

data class CreateTransaction(
    val userToken: String,
    val params: TransactionParams)

data class TransactionParams(
    var userId: String,
    var eventId: String,
    var passkey: String? = null,
    var tickets: List<ShopTicket>)

data class ShopTicket(
    var guestTypeId: String = "",
    var quantity: Int = 0,
    var holder: List<Holder>?)

data class Holder(
    var email: String? = "")