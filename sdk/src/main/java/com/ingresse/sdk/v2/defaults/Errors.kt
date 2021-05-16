package com.ingresse.sdk.v2.defaults

class Errors {

    object HttpError {
        const val UNAUTHORIZED = 401
    }

    object Code6xxx {
        const val INVALID_TOKEN = 6062
        const val EXPIRED_TOKEN = 6065
    }

    companion object {
        const val INGRESSE_ERROR_PREFIX = "[Ingresse Exception Error]"
        const val TOKEN_EXPIRED = "token expired"
        const val EMPTY_BODY_RESPONSE = "Response with empty body"

        val tokenError = listOf(
            Code6xxx.INVALID_TOKEN,
            Code6xxx.EXPIRED_TOKEN,
            HttpError.UNAUTHORIZED
        )
    }
}
