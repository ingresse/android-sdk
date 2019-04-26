package com.ingresse.sdk.services

import com.google.gson.reflect.TypeToken
import com.ingresse.sdk.IngresseClient
import com.ingresse.sdk.base.IngresseCallback
import com.ingresse.sdk.base.Response
import com.ingresse.sdk.base.RetrofitCallback
import com.ingresse.sdk.builders.Host
import com.ingresse.sdk.builders.URLBuilder
import com.ingresse.sdk.errors.APIError
import com.ingresse.sdk.model.request.CompanyLogin
import com.ingresse.sdk.model.response.CompanyLoginJSON
import com.ingresse.sdk.model.response.UserAuthTokenJSON
import com.ingresse.sdk.request.Auth
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.scalars.ScalarsConverterFactory

class AuthService(private val client: IngresseClient) {
    private var host = Host.API
    private var service: Auth

    private var mCompanyLoginCall: Call<String>? = null
    private var mRenewAuthTokenCall: Call<String>? = null

    init {
        val adapter = Retrofit.Builder()
                .addConverterFactory(ScalarsConverterFactory.create())
                .baseUrl(URLBuilder(host, client.environment).build())
                .build()

        service = adapter.create(Auth::class.java)
    }

    /**
     * Method to cancel a company login request
     */
    fun cancelCompanyLogin() = mCompanyLoginCall?.cancel()

    /**
     * Method to cancel a company login request
     */
    fun cancelRenewAuthToken() = mRenewAuthTokenCall?.cancel()

    /**
     * Company login with email and password
     *
     * @param request - all parameters used in auth interface
     * @param onSuccess - success callback
     * @param onError - error callback
     */
    fun companyLoginWithEmail(request: CompanyLogin, onSuccess: (CompanyLoginJSON) -> Unit, onError: (APIError) -> Unit) {
        mCompanyLoginCall = service.companyLoginWithEmail(
            apikey = client.key,
            email = request.email,
            password = request.password
        )

        val callback = object : IngresseCallback<Response<CompanyLoginJSON>?> {
            override fun onSuccess(data: Response<CompanyLoginJSON>?) {
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

        val type = object: TypeToken<Response<CompanyLoginJSON>>() {}.type
        mCompanyLoginCall?.enqueue(RetrofitCallback(type, callback))
    }

    /**
     * Renew user auth token
     *
     * @param userToken - Token of logged user
     * @param onSuccess - success callback
     * @param onError - error callback
     */
    fun renewAuthToken(userToken: String, onSuccess: (String) -> Unit, onError: (APIError) -> Unit) {
        mRenewAuthTokenCall = service.renewAuthToken(
            apikey = client.key,
            userToken = userToken
        )

        val callback = object : IngresseCallback<Response<UserAuthTokenJSON>?> {
            override fun onSuccess(data: Response<UserAuthTokenJSON>?) {
                val response = data?.responseData?.authToken ?: return onError(APIError.default)
                onSuccess(response)
            }

            override fun onError(error: APIError) = onError(error)

            override fun onRetrofitError(error: Throwable) {
                val apiError = APIError()
                apiError.message = error.localizedMessage
                onError(apiError)
            }
        }

        val type = object: TypeToken<Response<UserAuthTokenJSON>>() {}.type
        mRenewAuthTokenCall?.enqueue(RetrofitCallback(type, callback))
    }
}