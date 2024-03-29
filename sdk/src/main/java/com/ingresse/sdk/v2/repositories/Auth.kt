package com.ingresse.sdk.v2.repositories

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.ingresse.sdk.IngresseClient
import com.ingresse.sdk.builders.Host
import com.ingresse.sdk.builders.URLBuilder
import com.ingresse.sdk.v2.models.base.IngresseResponse
import com.ingresse.sdk.v2.models.request.FaceBankLogin
import com.ingresse.sdk.v2.models.request.FacebookLogin
import com.ingresse.sdk.v2.models.request.Login
import com.ingresse.sdk.v2.models.request.RenewAuthToken
import com.ingresse.sdk.v2.models.response.AuthTokenJSON
import com.ingresse.sdk.v2.models.response.login.CompanyLoginJSON
import com.ingresse.sdk.v2.models.response.login.LoginDataJSON
import com.ingresse.sdk.v2.parses.model.Result
import com.ingresse.sdk.v2.parses.responseParser
import com.ingresse.sdk.v2.services.AuthService
import kotlinx.coroutines.CoroutineDispatcher
import retrofit2.Retrofit
import retrofit2.converter.scalars.ScalarsConverterFactory

class Auth(private val client: IngresseClient) {

    private val service: AuthService

    init {
        val adapter = Retrofit.Builder()
            .addConverterFactory(ScalarsConverterFactory.create())
            .baseUrl(URLBuilder(Host.API, client.environment, client.customPrefix).build())
            .build()

        service = adapter.create(AuthService::class.java)
    }

    suspend fun companyLogin(
        dispatcher: CoroutineDispatcher,
        request: Login,
    ): Result<IngresseResponse<CompanyLoginJSON>> {
        val type = object : TypeToken<IngresseResponse<CompanyLoginJSON>>() {}.type
        return responseParser(dispatcher, type) {
            service.companyLogin(
                apikey = client.key,
                email = request.email,
                password = request.password
            )
        }
    }

    suspend fun login(
        dispatcher: CoroutineDispatcher,
        request: Login,
    ): Result<IngresseResponse<LoginDataJSON>> {
        val type = object : TypeToken<IngresseResponse<LoginDataJSON>>() {}.type
        return responseParser(dispatcher, type) {
            service.login(
                apikey = client.key,
                email = request.email,
                password = request.password,
                device = Gson().toJson(request.device)
            )
        }
    }

    suspend fun loginWithFacebook(
        dispatcher: CoroutineDispatcher,
        request: FacebookLogin,
    ): Result<IngresseResponse<LoginDataJSON>> {
        val type = object : TypeToken<IngresseResponse<LoginDataJSON>>() {}.type
        return responseParser(dispatcher, type) {
            service.loginWithFacebook(
                apikey = client.key,
                email = request.email,
                facebookToken = request.facebookToken,
                facebookUserId = request.facebookUserId,
                device = Gson().toJson(request.device)
            )
        }
    }

    suspend fun loginWithFaceBank(dispatcher: CoroutineDispatcher, request: FaceBankLogin): Result<IngresseResponse<LoginDataJSON>> {
        val type = object : TypeToken<IngresseResponse<LoginDataJSON>>() {}.type
        return responseParser(dispatcher, type) {
            service.loginWithFaceBank(
                apikey = client.key,
                code = request.code,
                redirectUri = request.redirectUri,
                device = Gson().toJson(request.device)
            )
        }
    }

    suspend fun renewAuthToken(
        dispatcher: CoroutineDispatcher,
        request: RenewAuthToken,
    ): Result<IngresseResponse<AuthTokenJSON>> {
        val type = object : TypeToken<IngresseResponse<AuthTokenJSON>>() {}.type
        return responseParser(dispatcher, type) {
            service.renewAuthToken(
                apikey = client.key,
                token = request.token
            )
        }
    }
}
