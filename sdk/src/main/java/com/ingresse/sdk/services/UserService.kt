package com.ingresse.sdk.services

import com.google.gson.reflect.TypeToken
import com.ingresse.sdk.IngresseClient
import com.ingresse.sdk.base.IngresseCallback
import com.ingresse.sdk.base.Response
import com.ingresse.sdk.base.RetrofitCallback
import com.ingresse.sdk.builders.*
import com.ingresse.sdk.errors.APIError
import com.ingresse.sdk.model.request.UserBasicInfos
import com.ingresse.sdk.model.request.UserData
import com.ingresse.sdk.model.response.UserDataJSON
import com.ingresse.sdk.model.response.UserUpdatedDataJSON
import com.ingresse.sdk.model.response.UserUpdatedJSON
import com.ingresse.sdk.request.User
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory

class UserService(private val client: IngresseClient) {
    private var host = Host.API
    private var service: User

    private var mUserDataCall: Call<String>? = null
    private var mUpdateBasicInfosCall: Call<String>? = null

    init {
        val httpClient = ClientBuilder(client)
            .addRequestHeaders()
            .build()

        val adapter = Retrofit.Builder()
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .client(httpClient)
                .baseUrl(URLBuilder(host, client.environment).build())
                .build()

        service = adapter.create(User::class.java)
    }

    /**
     * Method to cancel user data request
     */
    fun cancelUserData() = mUserDataCall?.cancel()

    /**
     * Method to cancel user update basic infos request
     */
    fun cancelUpdateBasicInfos() = mUpdateBasicInfosCall?.cancel()

    /**
     * Get user data
     *
     * @param request - all parameters used for retrieving user data
     * @param onSuccess - success callback
     * @param onError - error callback
     */
    fun getUserData(request: UserData, onSuccess: (UserDataJSON) -> Unit, onError: (APIError) -> Unit) {
        if (client.authToken.isEmpty()) return onError(APIError.default)

        val fields = listOf("id", "name", "lastname",
                "document", "email", "zip",
                "number", "complement", "city",
                "state", "street", "district",
                "phone", "verified", "fbUserId",
                "type", "pictures", "picture")

        val customFields = request.fields?.let { it } ?: fields.joinToString(",")
        mUserDataCall = service.getUserData(
                userId = request.userId,
                apikey = client.key,
                userToken = request.userToken,
                fields = customFields
        )

        val callback = object : IngresseCallback<Response<UserDataJSON>?> {
            override fun onSuccess(data: Response<UserDataJSON>?) {
                val response = data?.responseData ?: return onError(APIError.default)
                onSuccess(response)
            }

            override fun onError(error: APIError) = onError(error)

            override fun onRetrofitError(error: Throwable) {
                val apiError = APIError()
                apiError.message = error.localizedMessage
                onError(apiError)
            }
        }

        val type = object : TypeToken<Response<UserDataJSON>>() {}.type
        mUserDataCall?.enqueue(RetrofitCallback(type, callback))
    }

    /**
     * Update user basic infos
     *
     * @param request - all parameters used for update user infos
     * @param onSuccess - success callback
     * @param onError - error callback
     */
    fun updateBasicInfos(request: UserBasicInfos, onSuccess: (UserUpdatedDataJSON) -> Unit, onError: (APIError) -> Unit) {
        mUpdateBasicInfosCall = service.updateBasicInfos(
            userId = request.userId,
            apikey = client.key,
            userToken = request.userToken,
            params = request
        )

        val callback = object : IngresseCallback<Response<UserUpdatedJSON>?> {
            override fun onSuccess(data: Response<UserUpdatedJSON>?) {
                val response = data?.responseData ?: return onError(APIError.default)

                if (!response.message.isNullOrEmpty()) {
                    val apiError = APIError()
                    apiError.message = response.message.joinToString(", ")
                    apiError.title = "Verifique suas informações"
                    apiError.code = 0
                    onError(apiError)
                    return
                }

                val responseData = response.data ?: return onError(APIError.default)
                onSuccess(responseData)
            }

            override fun onError(error: APIError) = onError(error)

            override fun onRetrofitError(error: Throwable) {
                val apiError = APIError()
                apiError.message = error.localizedMessage
                onError(apiError)
            }
        }

        val type = object : TypeToken<Response<UserUpdatedJSON>>() {}.type
        mUpdateBasicInfosCall?.enqueue(RetrofitCallback(type, callback))
    }
}