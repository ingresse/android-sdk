package com.ingresse.sdk.model.response

data class PaymentTransactionJSON(
        var transactionId: String? = "",
        var status: String? = "",
        var creditCard: PaymentMethodJSON? = null,
        var message: String? = ""
)

data class PaymentMethodJSON(
        var type: String? = "",
        var installments: List<InstallmentJSON>? = emptyList()
)

data class InstallmentJSON(
        var quantity: Int? = 0,
        var value: Double? = 0.0,
        var total: Double? = 0.0,
        var taxValue: Double? = 0.0,
        var shippingCost: Double = 0.0
)


