package com.ingresse.sdk.model.response

data class TransactionReportJSON(
    val id: String? = "",
    val totalCustomers: String? = "",
    val totalApprovedCustomers: String? = "",
    val totalTransactions: String? = "",
    val approvedTransactions: String? = "",
    val declinedTransactions: String? = "",
    val cancelledTransactions: String? = "",
    val refundedTransactions: String? = "",
    val errorTransactions: String? = "",
    val pendingTransactions: String? = "",
    val waitingTransactions: String? = "",
    val limitExceededTransactions: String? = "",
    val summaryByDay: List<SummaryByDayJSON>?
)

data class SummaryByDayJSON(
    val date: String? = "",
    val approvedTransactions: String? = "",
    val declinedTransactions: String? = "",
    val cancelledTransactions: String? = "",
    val refundedTransactions: String? = "",
    val errorTransactions: String? = "",
    val pendingTransactions: String? = "",
    val waitingTransactions: String? = "",
    val limitExceededTransactions: String? = ""
)