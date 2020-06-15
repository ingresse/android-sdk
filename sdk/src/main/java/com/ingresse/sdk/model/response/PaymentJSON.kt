package com.ingresse.sdk.model.response

data class PaymentJSON(
    var boleto: String? = "",
    var message: String? = "",
    var status: String? = "",
    var tax: Double? = 0.0,
    var total: String? = "",
    var transactionId: String? = "",
    var declinedReason: DeclinedReasonJSON? = null
)

data class DeclinedReasonJSON(
        val message: String? = "",
        val declinedBy: String? = "",
        val code: String? = "",
        val createdAt: String? = ""
)