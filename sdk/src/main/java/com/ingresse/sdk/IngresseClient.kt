package com.ingresse.sdk

import com.ingresse.sdk.url.builder.Environment

class IngresseClient(val key: String,
                     val authToken: String,
                     val userAgent: String,
                     val environment: Environment,
                     val debug: Boolean = false)
