package com.ingresse.sdk.base

import com.ingresse.sdk.helper.ERROR_PREFIX
import com.ingresse.sdk.errors.APIError
import com.google.gson.Gson
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.lang.reflect.Type

class RetrofitCallback<T>(val type: Type, val callback: IngresseCallback<T>) : Callback<String> {
    override fun onResponse(call: Call<String>, response: Response<String>) {
        val body = response.body()

        if (body == null || body == "") {
            callback.onError(APIError.default)
            return
        }

        val gson = Gson()
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

        if (errorData == null) {
            callback.onError(APIError.default)
            return
        }

        val error = APIError.Builder()
                .setCode(errorData.code)
                .setError(errorData.message)
                .setCategory(errorData.category)
                .build()

        callback.onError(error)
    }

    override fun onFailure(call: Call<String>, t: Throwable) {
        callback.onRetrofitError(t)
    }
}