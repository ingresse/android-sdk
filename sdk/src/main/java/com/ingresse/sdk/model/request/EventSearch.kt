package com.ingresse.sdk.model.request

enum class EventsCategory(val slug: String?) {
    FESTAS_E_BALADAS("festas-e-baladas"),
    SHOWS_E_FESTIVAIS("shows-e-festivais"),
    TEATRO_E_CULTURA("teatro-e-cultura"),
    UNIVERSITARIO("universitario"),
    GASTRONOMIA("gastronomia");
}

data class EventSearch(val title: String? = null,
                       val category: String? = null,
                       val state: String? = null,
                       val term: String? = null,
                       val size: Int = 20,
                       val from: String? = "now-6h",
                       val to: String? = null,
                       val orderBy: String = "sessions.dateTime",
                       val offset: Int = 0)