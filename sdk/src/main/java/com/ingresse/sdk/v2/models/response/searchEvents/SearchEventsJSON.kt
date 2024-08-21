package com.ingresse.sdk.v2.models.response.searchEvents

import com.google.gson.annotations.SerializedName

data class SearchEventsJSON(
    @SerializedName("accepted_currencies")
    val acceptedCurrencies: String? = null,
    val attributes: AttributesJSON?,
    val categories: List<CategoryJSON>?,
    val companyId: Int?,
    val createdAt: String?,
    val description: String?,
    val id: Int?,
    val place: PlaceJSON?,
    val poster: PosterJSON?,
    val producerId: Int?,
    val sessions: List<SessionsJSON>?,
    val slug: String?,
    val staff: StaffJSON?,
    val status: StatusJSON?,
    val title: String?,
    val timezone: String?,
    val updatedAt: String?,
    val usersPermission: List<Int>?,
)
