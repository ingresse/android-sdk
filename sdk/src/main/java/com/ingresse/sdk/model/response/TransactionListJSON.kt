package com.ingresse.sdk.model.response

data class TransactionListJSON(
    val id: String? = "",
    val paymentoption: String? = "",
    val paymenttype: String? = "",
    val installments: String? = "",
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
    val app_id: String? = "",
    val postbackUrl: String? = "",
    val creationdate: String? = "",
    val modificationdate: String? = "",
    val operatorId: String? = "",
    val salesgroupId: String? = "",
    val creditCard: TransactionCreditCardJSON?,
    val customer: TransactionUserJSON?,
    val event: TransactionEventJSON?,
    val session: TransactionSessionJSON?,
    val refund: TransactionRefundJSON?,
    val payment: TransactionPaymentJSON?,
    val salesgroup: TransactionSalesGroupJSON?,
    val operator: TransactionUserJSON?)

data class TransactionRefundJSON(
        val date: String? = "",
        val operator: String? = "",
        val reason: String? = "")

data class TransactionCreditCardJSON(
        val firstDigits: String? = "",
        val lastDigits: String? = "")

data class TransactionPaymentJSON(
        val id: String? = "",
        val externalId: String? = "",
        val details: PaymentDetailsJSON?,
        val declinedReason: PaymentDeclinedReasonJSON?,
        val creditCard: PaymentCreditCardJSON?,
        val bankBillet: PaymentBankBilletJSON?,
        val acquirer: String? = "")

data class PaymentDetailsJSON(
        val tid: String? = "",
        val nsu: String? = "",
        val authorizationCode: String? = "",
        val acquirerPaymentId: String? = "")

data class PaymentDeclinedReasonJSON(
        val message: String? = "",
        val declinedBy: String? = "",
        val createdAt: String? = "",
        val code: String? = "")

data class PaymentCreditCardJSON(
        val token: String? = "",
        val masked: String? = "",
        val expiration: String? = "",
        val brand: String? = "",
        val holder: String? = "")

data class PaymentBankBilletJSON(
        val url: String? = "",
        val provider: String = "",
        val expiration: String? = "",
        val barCode: String? = ""
)