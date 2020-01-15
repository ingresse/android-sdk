package com.ingresse.sdk.services

import com.google.gson.reflect.TypeToken
import com.ingresse.sdk.IngresseClient
import com.ingresse.sdk.base.IngresseCallback
import com.ingresse.sdk.base.Response
import com.ingresse.sdk.base.RetrofitCallback
import com.ingresse.sdk.builders.Host
import com.ingresse.sdk.builders.URLBuilder
import com.ingresse.sdk.errors.APIError
import com.ingresse.sdk.helper.Block
import com.ingresse.sdk.helper.ErrorBlock
import com.ingresse.sdk.model.request.Login
import com.ingresse.sdk.model.request.LoginWithFacebook
import com.ingresse.sdk.model.response.CompanyLoginJSON
import com.ingresse.sdk.model.response.LoginDataJSON
import com.ingresse.sdk.model.response.LoginJSON
import com.ingresse.sdk.model.response.UserAuthTokenJSON
import com.ingresse.sdk.request.Auth
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.scalars.ScalarsConverterFactory
import java.io.IOException

class AuthService(private val client: IngresseClient) {
    private var host = Host.API
    private var service: Auth

    private var mLoginCall: Call<String>? = null
    private var mLoginWithFacebook: Call<String>? = null
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
     * Method to cancel a login request
     */
    fun cancelLogin() = mLoginCall?.cancel()

    /**
     * Method to cancel a login with facebook request
     */
    fun cancelLoginWithFacebook() = mLoginWithFacebook?.cancel()

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
    fun companyLoginWithEmail(request: Login, onSuccess: (CompanyLoginJSON) -> Unit, onError: ErrorBlock, onConnectionError: (Throwable) -> Unit, onTokenExpired: Block) {
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
                if (error is IOException) return onConnectionError(error)

                val apiError = APIError()
                apiError.message = error.localizedMessage
                onError(apiError)
            }
            override fun onTokenExpired() = onTokenExpired()
        }

        val type = object: TypeToken<Response<CompanyLoginJSON>>() {}.type
        mCompanyLoginCall?.enqueue(RetrofitCallback(type, callback))
    }

    /**
     * Login with email and password
     *
     * @param request - all parameters used in auth interface
     * @param onSuccess - success callback
     * @param onError - error callback
     */
    fun login(request: Login, onSuccess: (LoginDataJSON) -> Unit, onError: ErrorBlock, onConnectionError: (Throwable) -> Unit, onTokenExpired: Block) {
        mLoginCall = service.login(
                apikey = client.key,
                email = request.email,
                password = request.password
        )

        val callback = object : IngresseCallback<Response<LoginJSON>?> {
            override fun onSuccess(data: Response<LoginJSON>?) {
                val response = data?.responseData ?: return onError(APIError.default)
                if (!response.status) return onError(APIError.Builder()
                        .setCode(0)
                        .setMessage(response.message ?: "")
                        .build())
                if (response.data == null) return onError(APIError.default)
                onSuccess(response.data)
            }

            override fun onError(error: APIError) = onError(error)

            override fun onRetrofitError(error: Throwable) {
                if (error is IOException) return onConnectionError(error)

                val apiError = APIError()
                apiError.message = error.localizedMessage
                onError(apiError)
            }
            override fun onTokenExpired() = onTokenExpired()
        }

        val type = object: TypeToken<Response<LoginJSON>>() {}.type
        mLoginCall?.enqueue(RetrofitCallback(type, callback))
    }

    /**
     * Login with Facebook
     *
     * @param onSuccess - success callback
     * @param onError -  error callback
     * @param onConnectionError - connection error callback
     * @param onTokenExpired - token expired callback
     */
    fun loginWithFacebook(request: LoginWithFacebook, onSuccess: (LoginDataJSON) -> Unit, onError: ErrorBlock, onConnectionError: (Throwable) -> Unit, onTokenExpired: Block) {
        mLoginWithFacebook = service.loginWithFacebook(
                apikey = client.key,
                email = request.email,
                fbToken = request.fbToken,
                fbUserId = request.fbUserId
        )

        val callback = object : IngresseCallback<Response<LoginJSON>?> {
            override fun onSuccess(data: Response<LoginJSON>?) {
                val response = data?.responseData ?: return onError(APIError.default)
                if (!response.status) return onError(APIError.Builder()
                        .setCode(0)
                        .setMessage(response.message ?: "")
                        .build())
                if (response.data == null) return onError(APIError.default)
                onSuccess(response.data)
            }

            override fun onError(error: APIError) = onError(error)

            override fun onRetrofitError(error: Throwable) {
                if (error is IOException) return onConnectionError(error)

                val apiError = APIError()
                apiError.message = error.localizedMessage
                onError(apiError)
            }

            override fun onTokenExpired() = onTokenExpired()
        }

        val type = object : TypeToken<Response<LoginJSON>?>() {}.type
        mLoginWithFacebook?.enqueue(RetrofitCallback(type, callback))
    }

    /**
     * Renew user auth token
     *
     * @param userToken - Token of logged user
     * @param onSuccess - success callback
     * @param onError - error callback
     */
    fun renewAuthToken(userToken: String, onSuccess: (String) -> Unit, onError: ErrorBlock, onTokenExpired: Block) {
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

            override fun onTokenExpired() = onTokenExpired()
        }

        val type = object: TypeToken<Response<UserAuthTokenJSON>>() {}.type
        mRenewAuthTokenCall?.enqueue(RetrofitCallback(type, callback))
    }
}