package com.ingresse.sdk.v2.parses

import com.google.gson.Gson
import com.ingresse.sdk.v2.defaults.Errors
import com.ingresse.sdk.v2.defaults.Errors.Companion.EMPTY_BODY_RESPONSE
import com.ingresse.sdk.v2.defaults.Errors.Companion.INGRESSE_ERROR_PREFIX
import com.ingresse.sdk.v2.defaults.Errors.Companion.TOKEN_EXPIRED
import com.ingresse.sdk.v2.models.base.Error
import com.ingresse.sdk.v2.models.base.IngresseError
import com.ingresse.sdk.v2.parses.model.Result
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.ResponseBody
import retrofit2.Response
import java.io.IOException
import java.lang.reflect.Type

@Suppress("TooGenericExceptionCaught")
suspend fun <T> responseParser(
    dispatcher: CoroutineDispatcher = Dispatchers.Default,
    type: Type,
    call: suspend () -> Response<*>,
): Result<T> =
    withContext(dispatcher) {
        try {
            val response: Response<*> = call.invoke()
            val body = response.body()
            val errorBody = response.errorBody()
            val gson = Gson()

            if (errorBody != null) {
                return@withContext gson.parseErrorBody<T>(errorBody)
            }

            if (response.code() == 204 && body == null) {
                return@withContext Result.success<T>(null)
            }

            if (response.code() == 200 && body == null) {
                return@withContext parseEmptyBodyError<T>()
            }

            if (body.toString().contains(INGRESSE_ERROR_PREFIX)) {
                var parsedBody: String? = body.toString()

                if (body is Map<*, *>) {
                    parsedBody = gson.toJson(body).toString()
                }

                return@withContext gson.parseIngresseError<T>(parsedBody.orEmpty())
            }

            var parsedBody: String? = body.toString()

            if (body is Map<*, *>) {
                parsedBody = gson.toJson(body).toString()
            }

            return@withContext gson.parseSuccessObject<T>(parsedBody, type)
        } catch (ioException: IOException) {
            Result.connectionError()
        } catch (throwable: Throwable) {
            Result.error(null, throwable)
        }
    }

private fun <T> Gson.parseSuccessObject(body: String?, type: Type): Result<T> {
    val result = fromJson<T>(body, type)
    return Result.success(result)
}

private fun <T> parseEmptyBodyError(): Result<T> {
    val throwable = Throwable(EMPTY_BODY_RESPONSE)
    return Result.error(null, throwable)
}

private fun <T> Gson.parseIngresseError(body: String): Result<T> {
    val result = fromJson(body, IngresseError::class.java)
    val error = result.responseError

    if (Errors.tokenError.contains(error.code)) {
        return Result.tokenExpired(error.code)
    }

    val message = "[${error.category}] ${error.message}"
    val throwable = Throwable(message)
    return Result.error(error.code, throwable)
}

private fun <T> Gson.parseErrorBody(errorBody: ResponseBody): Result<T> {
    val result = fromJson(errorBody.charStream(), Error::class.java)
    val message = "[${result.category}] ${result.message}"
    val throwable = Throwable(message)

    if (message.contains(TOKEN_EXPIRED, ignoreCase = true)) {
        return Result.tokenExpired(result.code)
    }

    return Result.error(result.code, throwable)
}
