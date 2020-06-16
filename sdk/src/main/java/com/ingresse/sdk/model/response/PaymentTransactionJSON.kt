package com.ingresse.sdk.model.response

import com.google.gson.annotations.SerializedName
import com.ingresse.sdk.helper.CREDIT_CARD_METHOD

data class PaymentTransactionJSON(var data: PaymentTransactionDataJSON? = null)

data class PaymentTransactionDataJSON(
        var transactionId: String? = "",
        var status: String? = "",
        var availablePaymentMethods: PaymentMethodJSON? = null,
        var message: String? = ""
)

data class PaymentMethodJSON(
        @SerializedName(CREDIT_CARD_METHOD)
        var creditCard: CreditCardMethodJSON? = null
)

data class CreditCardMethodJSON(
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


