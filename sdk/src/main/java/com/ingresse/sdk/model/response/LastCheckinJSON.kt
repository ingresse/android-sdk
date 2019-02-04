package com.ingresse.sdk.model.response

data class LastCheckinJSON(
        val id: Long,
        val timestamp: Long,
        val holder: UserJSON,
        val operator: UserJSON)