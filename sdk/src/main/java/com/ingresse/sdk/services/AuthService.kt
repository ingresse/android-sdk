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
import com.ingresse.sdk.model.request.UpdatePassword
import com.ingresse.sdk.model.request.ValidateHash
import com.ingresse.sdk.model.response.*
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
    private var mRecoverPasswordCall: Call<String>? = null
    private var mValidateHashCall: Call<String>? = null
    private var mUpdatePasswordCall: Call<String>? = null

    init {
        val adapter = Retrofit.Builder()
                .addConverterFactory(ScalarsConverterFactory.create())
                .baseUrl(URLBuilder(host, client.environment, client.customPrefix).build())
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
     * Method to cancel a recover password request
     */
    fun cancelRecoverPassword() = mRecoverPasswordCall?.cancel()

    /**
     * Method to cancel a validate hash request
     */
    fun cancelValidateHash() = mValidateHashCall?.cancel()

    /**
     * Method to cancel a update password request
     */
    fun cancelUpdatePassword() = mUpdatePasswordCall?.cancel()

    /**
     * Company login with email and password
     *
     * @param request - all parameters used in auth interface
     * @param onSuccess - success callback
     * @param onError - error callback
     */
    @Deprecated(
        message = "This call will no longer be maintained. Use V2 replacements.",
        replaceWith = ReplaceWith(
            expression = "Auth(client).companyLoginWithEmail(request = Login())",
            imports = [
                "com.ingresse.sdk.v2.repositories",
                "com.ingresse.sdk.v2.models.request.Login",
            ]
        )
    )
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
    @Deprecated(
        message = "This call will no longer be maintained. Use V2 replacements.",
        replaceWith = ReplaceWith(
            expression = "Auth(client).login(request = Login())",
            imports = [
                "com.ingresse.sdk.v2.repositories",
                "com.ingresse.sdk.v2.models.request.Login",
            ]
        )
    )
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
    @Deprecated(
        message = "This call will no longer be maintained. Use V2 replacements.",
        replaceWith = ReplaceWith(
            expression = "Auth(client).loginWithFacebook(request = FacebookLogin())",
            imports = [
                "com.ingresse.sdk.v2.repositories",
                "com.ingresse.sdk.v2.models.request.FacebookLogin",
            ]
        )
    )
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
    @Deprecated(
        message = "This call will no longer be maintained. Use V2 replacements.",
        replaceWith = ReplaceWith(
            expression = "Auth(client).renewAuthToken(request = RenewAuthToken())",
            imports = [
                "com.ingresse.sdk.v2.repositories",
                "com.ingresse.sdk.v2.models.request.RenewAuthToken",
            ]
        )
    )
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

    /**
     * Recover password
     *
     * @param email - user email
     * @param onSuccess - success callback
     * @param onError -  error callback
     * @param onConnectionError - connection error callback
     * @param onTokenExpired - token expired callback
     */
    @Deprecated(
        message = "This call will no longer be maintained. Use V2 replacements.",
        replaceWith = ReplaceWith(
            expression = "Password(client).requestReset(request = RequestReset())",
            imports = [
                "com.ingresse.sdk.v2.repositories",
                "com.ingresse.sdk.v2.models.request.RequestReset",
            ]
        )
    )
    fun recoverPassword(email: String, onSuccess: (AuthPasswordJSON) -> Unit, onError: ErrorBlock, onConnectionError: (Throwable) -> Unit, onTokenExpired: Block) {
        mRecoverPasswordCall = service.recoverPassword(
                apikey = client.key,
                email = email
        )

        val callback = object : IngresseCallback<Response<AuthPasswordJSON>?> {
            override fun onSuccess(data: Response<AuthPasswordJSON>?) {
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

        val type = object : TypeToken<Response<AuthPasswordJSON>?>() {}.type
        mRecoverPasswordCall?.enqueue(RetrofitCallback(type, callback))
    }

    /**
     * Validate recovery hash
     *
     * @param request - all parameters used in validate hash
     * @param onSuccess - success callback
     * @param onError -  error callback
     * @param onConnectionError - connection error callback
     * @param onTokenExpired - token expired callback
     */
    @Deprecated(
        message = "This call will no longer be maintained. Use V2 replacements.",
        replaceWith = ReplaceWith(
            expression = "Password(client).validateHash(request = ValidateHash())",
            imports = [
                "com.ingresse.sdk.v2.repositories",
                "com.ingresse.sdk.v2.models.request.ValidateHash",
            ]
        )
    )
    fun validateHash(request: ValidateHash, onSuccess: (ValidateHashJSON) -> Unit, onError: ErrorBlock, onConnectionError: (Throwable) -> Unit, onTokenExpired: Block) {
        mValidateHashCall = service.validateHash(
                apikey = client.key,
                email = request.email,
                hash = request.hash
        )

        val callback = object : IngresseCallback<Response<ValidateHashJSON>?> {
            override fun onSuccess(data: Response<ValidateHashJSON>?) {
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

        val type = object : TypeToken<Response<ValidateHashJSON>?>() {}.type
        mValidateHashCall?.enqueue(RetrofitCallback(type, callback))
    }

    /**
     * Update user password
     *
     * @param request - all parameters used in update password
     * @param onSuccess - success callback
     * @param onError -  error callback
     * @param onConnectionError - connection error callback
     * @param onTokenExpired - token expired callback
     */
    @Deprecated(
        message = "This call will no longer be maintained. Use V2 replacements.",
        replaceWith = ReplaceWith(
            expression = "Password(client).updatePassword(request = UpdatePassword())",
            imports = [
                "com.ingresse.sdk.v2.repositories",
                "com.ingresse.sdk.v2.models.request.UpdatePassword",
            ]
        )
    )
    fun updatePassword(request: UpdatePassword, onSuccess: (AuthPasswordJSON) -> Unit, onError: ErrorBlock, onConnectionError: (Throwable) -> Unit, onTokenExpired: Block) {
        mUpdatePasswordCall = service.updatePassword(
                apikey = client.key,
                email = request.email,
                password = request.password,
                hash = request.hash
        )

        val callback = object : IngresseCallback<Response<AuthPasswordJSON>?> {
            override fun onSuccess(data: Response<AuthPasswordJSON>?) {
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

        val type = object : TypeToken<Response<AuthPasswordJSON>?>() {}.type
        mUpdatePasswordCall?.enqueue(RetrofitCallback(type, callback))
    }
}