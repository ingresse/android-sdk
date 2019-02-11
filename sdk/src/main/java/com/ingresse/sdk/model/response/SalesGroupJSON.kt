package com.ingresse.sdk.model.response

data class SalesGroupJSON(
        val id: String? = "",
        val name: String? = "",
        val team: Array<TeamJSON> = emptyArray())