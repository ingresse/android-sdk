package com.ingresse.sdk.model.response

data class EventJSON(
    val categories: Array<CategoryJSON>? = emptyArray(),
    val admins: List<Int>? = emptyList(),
    val companyId: Int? = 1,
    val createdAt: String? = "",
    val description: String? = "",
    val id: Int? = 0,
    val place: PlaceJSON? = PlaceJSON(),
    val poster: PosterJSON? = PosterJSON(),
    val producerId: Int? = 0,
    val sessions: Array<EventSessionJSON>? = emptyArray(),
    val slug: String? = "",
    val status: StatusJSON? = StatusJSON(),
    val title: String? = "",
    val updatedAt: String? = "",
    val usersPermission: Array<Int>? = emptyArray())