package com.ingresse.sdk.model.response

data class EventJSON(
    val categories: Array<CategoryJSON>? = arrayOf(CategoryJSON()),
    val companyId: Int? = 1,
    val createdAt: String? = "",
    val description: String? = "",
    val id: Int? = 0,
    val place: PlaceJSON? = PlaceJSON(),
    val poster: PosterJSON? = PosterJSON(),
    val sessions: Array<EventSessionJSON>? = arrayOf(EventSessionJSON()),
    val slug: String? = "",
    val status: StatusJSON? = StatusJSON(),
    val title: String? = "",
    val updatedAt: String? = "",
    val usersPermission: ArrayList<Int>? = ArrayList())