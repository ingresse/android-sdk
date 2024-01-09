package com.ingresse.sdk.base

import java.net.URL

abstract class ErrorLogger {
    abstract fun logError(
        url: URL,
        requestData: String?,
        errorBody: String?,
        errorCode: Int?,
    )
}