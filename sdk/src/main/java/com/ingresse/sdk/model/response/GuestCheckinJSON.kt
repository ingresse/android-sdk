package com.ingresse.sdk.model.response

data class GuestCheckinJSON(
        val id: String,
        val code: String,
        val status: Int,
        val checked: Int,
        val lastUpdate: String,
        val owner: PersonJSON,
        val lastCheckin: LastCheckinJSON) {

    fun getStatus(): CheckinStatus {
        return when(status) {
            1 -> CheckinStatus.UPDATED
            2 -> CheckinStatus.NOT_FOUND
            3 -> CheckinStatus.ALREADY_UPDATED
            4 -> CheckinStatus.INVALID_SESSION
            5 -> CheckinStatus.SESSION_REQUIRED
            6 -> CheckinStatus.REFUNDED
            else -> CheckinStatus.NOT_UPDATED
        }
    }
}