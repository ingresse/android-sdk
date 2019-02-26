package com.ingresse.sdk.model.response

data class CreateTransactionJSON(
    val data: TransactionDataJSON?)

data class TransactionDataJSON(
    val transactionId: String? = "",
    val status: String? = "",
    val message: String? = "",
    val availablePaymentMethods: PaymentMethodKeyJSON?)