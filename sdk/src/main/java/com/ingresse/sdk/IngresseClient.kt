package com.ingresse.sdk

import com.ingresse.sdk.base.ErrorLogger
import com.ingresse.sdk.builders.Environment
import okhttp3.EventListener
import okhttp3.Interceptor

class IngresseClient(
    val key: String,
    val authToken: String,
    val userAgent: String,
    val environment: Environment,
    val customPrefix: String?,
    val debug: Boolean = false,
    val logger: ErrorLogger? = null,
)
