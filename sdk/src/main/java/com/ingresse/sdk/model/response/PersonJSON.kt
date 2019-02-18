package com.ingresse.sdk.model.response

data class PersonJSON(
        val id: Long,
        val name: String,
        val email: String,
        val picture: String? = null)
