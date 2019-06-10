package com.ingresse.sdk.model.response

import com.ingresse.sdk.base.SimpleArray

data class TransactionDetailsRefundJSON(
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
        val basket: SimpleArray<TransactionBasketJSON>?,
        val refund: RefundJSON?,
        val hasRefund: Boolean? = refund != null)