package com.ingresse.sdk.model.response

data class SalesGroupJSON(
        val id: String? = "",
        val name: String? = "",
        val team: List<TeamJSON> = emptyList())