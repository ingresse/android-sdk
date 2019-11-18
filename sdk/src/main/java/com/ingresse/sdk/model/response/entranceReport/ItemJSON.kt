package com.ingresse.sdk.model.response.entranceReport

import com.google.gson.annotations.SerializedName

data class ItemJSON(
    @SerializedName("_source")
    val source: SourceJSON? = null
)

data class SourceJSON(
    val archived: Boolean? = null,
    val available: Boolean? = null,
    val children: List<SourceJSON>? = null,
    val customTax: Boolean? = null,
    val description: String? = null,
    val eventId: Int? = null,
    val hidden: Boolean? = null,
    val id: Int? = null,
    val name: String? = null,
    val order: Int? = null,
    val parent: Int? = null,
    val quantity: Int? = null,
    val salable: Boolean? = null,
    val salePeriods: List<SalePeriodJSON>? = null,
    val type: String? = null,
    val value: ValueJSON? = null
)

data class SalePeriodJSON(
    val appCategoryId: Int? = null,
    val available: Boolean? = null,
    val finish: String? = null,
    val id: Int? = null,
    val start: String? = null
)

data class ValueJSON(
    val feeProducer: Double? = null,
    val feeSale: Double? = null,
    val finalPrice: Double? = null,
    val price: Double? = null,
    val taxDistribution: Double? = null
)