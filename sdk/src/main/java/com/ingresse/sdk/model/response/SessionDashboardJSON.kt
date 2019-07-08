package com.ingresse.sdk.model.response

data class SessionDashboardJSON(
    val event: String? = "",
    val sales: SalesDashboardJSON? = null
)

data class SalesDashboardJSON(
    val summary: SalesSummaryJSON? = null,
    val ticketTypes: List<SalesTicketTypeJSON>? = null
)

data class SalesSummaryJSON(
    val totalIngresseTax: Float? = 0.0f,
    val totalQuantity: Int? = 0,
    val totalAvailable: Int? = 0,
    val totalQuantitySold: Int? = 0,
    val totalFreepass: Int? = 0,
    val totalPaymentCost: Float? = 0.0f,
    val totalSales: Float? = 0.0f,
    val totalPendingTickets: Int? = 0,
    val partialRevenue: Int? = 0
)

data class SalesTicketTypeJSON(
    val id: String? = "",
    val name: String? = "",
    val typeId: String? = "",
    val typeName: String? = "",
    val totalQuantity: String? = "",
    val totalAvailable: Int? = 0,
    val totalQuantitySold: String? = "",
    val totalFreepass: String? = "",
    val totalSales: String? = "",
    val totalIngresseTax: String? = "",
    val pendingTickets: Int? = 0,
    val endSales: EndSalesJSON? = null
)

data class EndSalesJSON(
    val date: String? = "",
    val time: String? = ""
)