package com.ingresse.sdk.model.response

import com.google.gson.annotations.SerializedName

data class CashClosingJSON(
        val summary: CashSummaryJSON,
        val groups: List<CashGroupsJSON>,
        val events: List<CashEventJSON>
)

data class CashGroupsJSON(val group: CashGroupJSON)
data class CashGroupJSON(val id: Int, val name: String)

data class CashEventJSON(
        val id: String,
        val title: String,
        @SerializedName("accepted_currency")
        val acceptedCurrencies: String? = null,
        val summary: CashSummaryJSON
)

data class CashSummaryJSON(
        val processed: List<CashSummaryItemJSON>,
        val refunds: List<CashSummaryItemJSON>,
        val net: List<CashSummaryItemJSON>)

data class CashSummaryItemJSON(
        val paymentOption: String,
        val quantityTransactions: Int,
        val quantitySold: Int,
        val totalTicketPrice: Double,
        val totalTax: Double,
        val subtotal: Double,
        val details: List<CashSummaryItemDetailsJSON>?)

data class CashSummaryItemDetailsJSON(
        val paymentDetails: String,
        val quantityTransactions: Int,
        val quantitySold: Int,
        val totalTicketPrice: Double,
        val totalTax: Double,
        val subtotal: Double)