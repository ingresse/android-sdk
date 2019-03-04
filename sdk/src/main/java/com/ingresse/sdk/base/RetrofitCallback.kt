package com.ingresse.sdk.base

import com.ingresse.sdk.helper.ERROR_PREFIX
import com.ingresse.sdk.errors.APIError
import com.google.gson.Gson
import com.ingresse.sdk.helper.AUTHTOKEN_EXPIRED
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.lang.reflect.Type

class RetrofitCallback<T>(val type: Type, val callback: IngresseCallback<T>) : Callback<String> {
    override fun onResponse(call: Call<String>, response: Response<String>) {
        val body = response.body()
        val errorBody = response.errorBody()?.string()
        val gson = Gson()

        if (response.toString().contains(AUTHTOKEN_EXPIRED, true)){
            val apiError = APIError.Builder().setMessage("expired")
            callback.onError(apiError.build())
            return
        }

        if (!errorBody.isNullOrEmpty()) {
            try {
                val obj = gson.fromJson(errorBody, ErrorData::class.java)
                val apiError = APIError.Builder().setCode(obj.code ?: 0)
                callback.onError(apiError.build())
            } catch (e: RuntimeException) {
                callback.onError(APIError.default)
            }

            return
        }

        if (body.isNullOrEmpty()) {
            callback.onError(APIError.default)
            return
        }

        if (!body.contains(ERROR_PREFIX)) {
            try {
                val obj = gson.fromJson<T>(body, type)
                callback.onSuccess(obj)
            } catch (e: RuntimeException) {
                callback.onError(APIError.default)
            }

            return
        }

        val errorResponse = gson.fromJson(body, Error::class.java)
        val errorData = errorResponse.responseError

        val error = APIError.Builder()
                .setCode(errorData.code ?: 0)
                .setError(errorData.message ?: "")
                .setCategory(errorData.category ?: "")
                .build()

        callback.onError(error)
    }

    override fun onFailure(call: Call<String>, t: Throwable) {
        callback.onRetrofitError(t)
    }
}