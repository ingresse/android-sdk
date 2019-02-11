package com.ingresse.sdk.errors

class APIError {
    var code = 0
    var title = ""
    var message = ""
    var category = ""
    var description = ""

    class Builder {
        private val error: APIError = APIError()

        fun setCode(code: Int): Builder {
            error.code = code
            error.title = IngresseErrors.getTitle(code)
            error.message = IngresseErrors.getError(code)
            return this
        }

        fun setMessage(message: String): Builder {
            error.message = message
            return this
        }

        fun setTitle(title: String): Builder {
            error.title = title
            return this
        }

        fun setError(description: String): Builder {
            error.description = description
            return this
        }

        fun setCategory(category: String): Builder {
            error.category = category
            return this
        }

        fun build(): APIError {
            return error
        }
    }

    companion object {
        val default: APIError
            get() = Builder().setCode(0).build()
    }
}
