package com.ingresse.sdk.services

import com.google.gson.reflect.TypeToken
import com.ingresse.sdk.IngresseClient
import com.ingresse.sdk.base.IngresseCallback
import com.ingresse.sdk.base.Response
import com.ingresse.sdk.base.RetrofitCallback
import com.ingresse.sdk.errors.APIError
import com.ingresse.sdk.model.request.UserData
import com.ingresse.sdk.model.response.UserDataJSON
import com.ingresse.sdk.request.User
import com.ingresse.sdk.url.builder.Host
import com.ingresse.sdk.url.builder.URLBuilder
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.scalars.ScalarsConverterFactory

class UserService(private val client: IngresseClient) {
    private var host = Host.API
    private var service: User

    private var mUserDataCall: Call<String>? = null

    init {
        val adapter = Retrofit.Builder()
                .addConverterFactory(ScalarsConverterFactory.create())
                .baseUrl(URLBuilder(host, client.environment).build())
                .build()

        service = adapter.create(User::class.java)
    }

    /**
     * Method to cancel user data request
     */
    fun cancelUserData() {
        mUserDataCall?.cancel()
    }

    /**
     * Get user data
     *
     * @param request - all parameters used for retrieving user data
     * @param onSuccess - success callback
     * @param onError - error callback
     */
    fun getUserData(request: UserData, onSuccess: (UserDataJSON) -> Unit, onError: (APIError) -> Unit) {
        val fields = mutableListOf<String>()
        fields.add("id")
        fields.add("name")
        fields.add("lastname")
        fields.add("document")
        fields.add("email")
        fields.add("zip")
        fields.add("number")
        fields.add("complement")
        fields.add("city")
        fields.add("state")
        fields.add("street")
        fields.add("district")
        fields.add("phone")
        fields.add("verified")
        fields.add("fbUserId")
        fields.add("type")
        fields.add("pictures")
        fields.add("picture")

        val customField = request.fields?.let { it } ?: fields.joinToString(",")
        mUserDataCall = service.getUserData(
                userId = request.userId,
                apikey = client.key,
                userToken = request.userToken,
                fields = customField
        )

        val callback = object : IngresseCallback<Response<UserDataJSON>?> {
            override fun onSuccess(data: Response<UserDataJSON>?) {
                val response = data?.responseData
                if (response == null) {
                    onError(APIError.default)
                    return
                }

                onSuccess(response)
            }

            override fun onError(error: APIError) {
                onError(error)
            }

            override fun onRetrofitError(error: Throwable) {
                val apiError = APIError()
                apiError.message = error.localizedMessage
                onError(apiError)
            }
        }

        val type = object : TypeToken<Response<UserDataJSON>>() {}.type
        mUserDataCall?.enqueue(RetrofitCallback(type, callback))
    }
}