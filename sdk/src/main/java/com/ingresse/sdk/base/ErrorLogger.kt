package com.ingresse.sdk.base

import java.net.URL

abstract class ErrorLogger {
    abstract fun logError(
        url: URL,
        errorBody: String?,
        errorCode: Int?,
    )
}