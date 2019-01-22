package com.ingresse.sdk.services

import com.google.gson.reflect.TypeToken
import com.ingresse.sdk.IngresseClient
import com.ingresse.sdk.base.IngresseCallback
import com.ingresse.sdk.base.Response
import com.ingresse.sdk.base.RetrofitCallback
import com.ingresse.sdk.errors.APIError
import com.ingresse.sdk.helper.guard
import com.ingresse.sdk.model.request.Login
import com.ingresse.sdk.model.request.UserData
import com.ingresse.sdk.model.response.LoginJSON
import com.ingresse.sdk.model.response.UserDataJSON
import com.ingresse.sdk.request.Auth
import com.ingresse.sdk.url.builder.Host
import com.ingresse.sdk.url.builder.URLBuilder
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.scalars.ScalarsConverterFactory

class AuthService(private val client: IngresseClient) {
    private var host = Host.API
    private var service: Auth

    private var mLoginCall: Call<String>? = null
    private var mUserDataCall: Call<String>? = null

    init {
        val adapter = Retrofit.Builder()
            .addConverterFactory(ScalarsConverterFactory.create())
            .baseUrl(URLBuilder(host, client.environment).build())
            .build()

        service = adapter.create(Auth::class.java)
    }

    /**
     * Method to cancel a login request
     */
    fun cancelLogin() {
        mLoginCall?.cancel()
    }

    /**
     * Login with email and password
     *
     * @param request - all parameters used in auth interface
     * @param onSuccess - success callback
     * @param onError - error callback
     */
    fun loginWithEmail(request: Login, onSuccess: (LoginJSON) -> Unit, onError: (APIError) -> Unit) {
        mLoginCall = service.loginWithEmail(
            apikey = client.key,
            email = request.email,
            password = request.password
        )

        val callback = object : IngresseCallback<Response<LoginJSON>?> {
            override fun onSuccess(data: Response<LoginJSON>?) {
                val response = data?.responseData
                if (!guard(data, response)) {
                    onError(APIError.default)
                    return
                }

                response?.data?.let { userJSON ->
                    val request = UserData(userJSON.userId, userJSON.token)
                    getUserData(request, {userData ->
                        response.data?.data = userData
                        onSuccess(response)
                    }, {
                        onError(it)
                    })
                }
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

        val type = object: TypeToken<Response<LoginJSON>>() {}.type
        mLoginCall?.enqueue(RetrofitCallback(type, callback))
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
     * @param request - all parameters used for retrieve user data
     * @param onSuccess - success callback
     * @param onError - error callback
     */
    fun getUserData(request: UserData, onSuccess: (UserDataJSON) -> Unit, onError: (APIError) -> Unit) {
        val fields= listOf(
            "id",
            "name",
            "lastname",
            "document",
            "email",
            "zip",
            "number",
            "complement",
            "city",
            "state",
            "street",
            "district",
            "phone",
            "verified",
            "fbUserId",
            "type",
            "pictures",
            "picture")

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
                if (!guard(data, response)) {
                    onError(APIError.default)
                    return
                }

                onSuccess(response!!)
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

        val type = object: TypeToken<Response<UserDataJSON>>() {}.type
        mUserDataCall?.enqueue(RetrofitCallback(type, callback))
    }
}