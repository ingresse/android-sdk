package com.ingresse.sdk.model.response.entranceReport

import com.google.gson.annotations.SerializedName

class EntranceReportJSON(
    val items: List<ItemJSON>? = null,
    @SerializedName("sold_count")
    val soldCount: FilteredJSON<SoldJSON>? = null,
    @SerializedName("checked_count")
    val checkedCount: FilteredJSON<CheckedJSON>? = null,
    @SerializedName("started_at")
    val startedAt: FilteredJSON<StartedJSON>? = null,
    @SerializedName("finished_at")
    val finishedAt: FilteredJSON<FinishedJSON>? = null,
    @SerializedName("validations_per_hour")
    val validations: FilteredJSON<ValidationsJSON>? = null,
    @SerializedName("internal_item_validations")
    val internalItems: InternalItemsJSON? = null,
    @SerializedName("external_item_validations")
    val externalItems: ExternalGroupJSON? = null
)

data class CheckedJSON(
    @SerializedName("doc_count")
    val count: Int? = null
)

data class FinishedJSON(
    @SerializedName("doc_count")
    val count: Int? = null,
    val max: TimeJSON? = null
)

data class StartedJSON(
    @SerializedName("doc_count")
    val count: Int? = null,
    val min: TimeJSON? = null
)

data class SoldJSON(
    @SerializedName("doc_count")
    val count: Int? = null
)

data class ValidationsJSON(
    @SerializedName("doc_count")
    val count: Int? = null,
    @SerializedName("date_histogram")
    val histogram: HistogramJSON? = null
)

data class HistogramJSON(
    @SerializedName("buckets")
    val validations: List<ValidationsByHour>? = null
)

data class ValidationsByHour(
    @SerializedName("doc_count")
    val count: Int? = null,
    @SerializedName("key")
    val timestamp: Long? = null,
    @SerializedName("key_as_string")
    val datetime: String? = null
)

data class TimeJSON(
    @SerializedName("value")
    val timestamp: Long? = null,
    @SerializedName("value_as_string")
    val datetime: String? = null
)

data class FilteredJSON<T>(
    val filtered: T? = null
)
