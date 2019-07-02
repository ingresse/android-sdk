package com.ingresse.sdk.model.response

data class TransactionDetailsJSON(
    val id: String? = "",
    val paymentoption: String? = "",
    val paymenttype: String? = "",
    val installments: Int? = 0,
    val bankbillet_url: String? = "",
    val paymentdetails: String? = "",
    val document: String? = "",
    val transactionId: String? = "",
    val token: String? = "",
    val status: String? = "",
    val totalOrder: String? = "",
    val totalPaid: String? = "",
    val sum_up: String? = "",
    val paymentTax: String? = "",
    val interest: String? = "",
    val app_id: Int? = -1,
    val creationdate: String? = "",
    val modificationdate: String? = "",
    val operatorId: String? = "",
    val salesgroupId: Int? = 0,
    val creditCard: PaymentCardJSON?,
    val customer: TransactionUserJSON?,
    val operator: TransactionUserJSON?,
    val event: TransactionEventJSON?,
    val session: TransactionSessionJSON?,
    val basket: TransactionBasketJSON?,
    val refund: RefundJSON?,
    val hasRefund: Boolean? = refund != null)

data class PaymentCardJSON(
        val firstDigits: String? = "",
        val lastDigits: String? = "")

data class TransactionBasketJSON(
        val tickets: Array<TransactionTicketJSON>?)

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

data class BasketSessionsJSON(
        val id: Int? = 0,
        val dateTime: SessionDateJSON?)

data class RefundJSON(
        val operator: String? = "",
        val reason: String? = "",
        val date: String? = "")