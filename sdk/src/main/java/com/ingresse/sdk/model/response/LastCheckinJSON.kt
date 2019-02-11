package com.ingresse.sdk.model.response

data class LastCheckinJSON(
        val id: Long,
        val timestamp: Long,
        val holder: PersonJSON,
        val operator: PersonJSON)