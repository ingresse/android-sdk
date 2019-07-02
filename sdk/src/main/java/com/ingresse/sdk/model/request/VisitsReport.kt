package com.ingresse.sdk.model.request

data class VisitsReport(
        var eventId: String,
        var userToken: String,
        var from: String? = null,
        var to: String? = null)