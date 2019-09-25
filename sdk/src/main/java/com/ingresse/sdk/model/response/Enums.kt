package com.ingresse.sdk.model.response

enum class CheckinStatus {
    NOT_UPDATED,
    UPDATED,
    NOT_FOUND,
    ALREADY_UPDATED,
    INVALID_SESSION,
    SESSION_REQUIRED
}

enum class ShopStatus(val status: String) {
    AVAILABLE("available"),
    UNAVAILABLE("unavailable"),
    NOT_STARTED("notstarted"),
    FINISHED("finished"),
    SOLD_OUT("soldout");

    companion object {
        fun getStatus(status: String): ShopStatus =
            values().find { it.status.equals(status, true) } ?: UNAVAILABLE
    }
}