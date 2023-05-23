package com.ingresse.sdk.v2.models.response

import com.google.gson.annotations.SerializedName

data class EntranceReportJSON(
    @SerializedName("checked_count")
    val checkedCount: Int?,
    @SerializedName("sold_count")
    val soldCount: Int?,
    @SerializedName("percentage_validations")
    val percentageValidations: Int?,
    val duration: String?,

    @SerializedName("started_at")
    val startedAt: DateJSON?,
    @SerializedName("finished_at")
    val finishedAt: DateJSON?,

    @SerializedName("validations_per_hour")
    val validationsByHour: List<ValidationsByHourJSON>?,

    @SerializedName("validations_per_type")
    val validationsByType: List<ValidationsByTypeJSON>?,
) {

    data class ValidationsByHourJSON(
        @SerializedName("validation_date")
        val validationDate: String?,
        @SerializedName("validation_hour")
        val validationHour: Int?,
        @SerializedName("validation_timestamp")
        val validationTimestamp: Long?,
        val validated: Int?,
    )

    data class ValidationsByTypeJSON(
        val id: Any?,
        val validated: Int?,
        @SerializedName("total_sold")
        val totalSold: Int?,
        @SerializedName("percentage_validations")
        val percentageValidations: Int?,
        val name: String?,
        val tickets: List<ValidationTicketJSON>?
    ) {
        data class ValidationTicketJSON(
            val id: Int?,
            val name: String?,
            val validated: Int?,
            @SerializedName("total_sold")
            val totalSold: Int?,
            @SerializedName("percentage_validations")
            val percentageValidations: Int?,
        )
    }

    data class DateJSON(
        @SerializedName("creationdate")
        val creationDate: String?,
        val timestamp: Long?,
    )
}