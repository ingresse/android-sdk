package com.ingresse.sdk.model.response

data class CheckinSessionJSON(
        val session: SessionJSON? = null,
        val owner: PersonJSON? = null,
        val lastStatus: CheckinStatusJSON? = null)

data class CheckinStatusJSON(
        val id: Long = 0,
        val timestamp: Long = 0,
        val operation: String = "",
        val operator: PersonJSON? = null,
        val holder: PersonJSON? = null)