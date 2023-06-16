package com.ingresse.sdk.model.response

data class EventJSON(
        val categories: List<CategoryJSON>? = emptyList(),
        val staff: EventStaffJSON? = null,
        val companyId: Int? = 1,
        val createdAt: String? = "",
        val description: String? = "",
        val id: Int? = 0,
        val place: PlaceJSON? = PlaceJSON(),
        val poster: PosterJSON? = PosterJSON(),
        val producerId: Int? = 0,
        val sessions: List<EventSessionJSON>? = emptyList(),
        val slug: String? = "",
        val status: StatusJSON? = StatusJSON(),
        val title: String? = "",
        val updatedAt: String? = "",
        val usersPermission: List<Int>? = emptyList(),
        val apply_ticket_validation_cpf : Boolean?)