package com.ingresse.sdk.model.response

data class VisitsReportJSON(
        var id: Int? = 0,
        var total: Int? = 0,
        var summaryByDay: VisitsSummaryByDayJSON?)

data class VisitsSummaryByDayJSON(
        var items: List<ItemsVisitsSummaryJSON>?)

data class ItemsVisitsSummaryJSON(
        var date: String? = "",
        var visits: Int? = 0)