package com.ingresse.sdk.v2.defaults

const val INGRESSE_ERROR_PREFIX = "[Ingresse Exception Error]"
const val AUTHTOKEN_EXPIRED = "token expired"
const val EMPTY_BODY_RESPONSE = "Response with empty body"

class Errors {
    class Code6xxx {
        companion object {
            const val INVALID_TOKEN = 6062
            const val EXPIRED_TOKEN = 6065
        }
    }

    companion object {
        val tokenError = listOf(
            Code6xxx.INVALID_TOKEN,
            Code6xxx.EXPIRED_TOKEN
        )
    }
}
