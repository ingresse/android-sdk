package com.ingresse.sdk.model.response.login

data class CompanyJSON(
        val id: Long = 0,
        val name: String = "",
        val logo: CompanyLogoJSON?
)