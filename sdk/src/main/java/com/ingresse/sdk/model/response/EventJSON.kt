package com.ingresse.sdk.model.response

data class EventJSON(val categories: Array<CategoryJSON>? = arrayOf(CategoryJSON()),
                     val description: String? = "",
                     val id: Int? = 0,
                     val place: PlaceJSON? = PlaceJSON(),
                     val poster: PosterJSON? = PosterJSON(),
                     val sessions: Array<DetailsSessionJSON>? = arrayOf(DetailsSessionJSON()),
                     val slug: String? = "",
                     val status: StatusJSON? = StatusJSON(),
                     val title: String? = "")