package com.ingresse.sdk.v2.models.response.searchEvents

data class SearchEventsJSON(
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
    val updatedAt: String?,
    val usersPermission: String?
)
