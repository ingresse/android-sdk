package com.ingresse.sdk.v2.models.response.eventDetails

data class DateJSON(
    val dateTime: DateTimeJSON?,
    val status: String?,
    val id: Int?
) {
    data class DateTimeJSON(
        val date: String?,
        val time: String?
    )
}
