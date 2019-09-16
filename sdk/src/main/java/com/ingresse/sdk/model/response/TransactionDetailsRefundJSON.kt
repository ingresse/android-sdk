package com.ingresse.sdk.model.response

data class TransactionDetailsRefundJSON(
        val id: String? = "",
        val refund: RefundJSON?,
        val hasRefund: Boolean? = refund != null)