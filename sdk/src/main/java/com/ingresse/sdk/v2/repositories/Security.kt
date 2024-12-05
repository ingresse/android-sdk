package com.ingresse.sdk.v2.repositories

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.ingresse.sdk.IngresseClient
import com.ingresse.sdk.builders.Host
import com.ingresse.sdk.builders.URLBuilder
import com.ingresse.sdk.v2.models.base.IngresseResponse
import com.ingresse.sdk.v2.models.request.ValidateMFACode
import com.ingresse.sdk.v2.models.response.security.UserAuthenticationJSON
import com.ingresse.sdk.v2.parses.model.Result
import com.ingresse.sdk.v2.parses.responseParser
import com.ingresse.sdk.v2.services.SecurityService
import kotlinx.coroutines.CoroutineDispatcher
import retrofit2.Retrofit
import retrofit2.converter.scalars.ScalarsConverterFactory

class Security(private val client: IngresseClient) {

    private val service: SecurityService

    init {
        val adapter = Retrofit.Builder()
            .addConverterFactory(ScalarsConverterFactory.create())
            .baseUrl(URLBuilder(Host.API, client.environment, client.customPrefix).build())
            .build()

        service = adapter.create(SecurityService::class.java)
    }

    suspend fun validateMFACode(
        dispatcher: CoroutineDispatcher,
        request: ValidateMFACode
    ): Result<IngresseResponse<UserAuthenticationJSON>> {
        val type = object : TypeToken<IngresseResponse<UserAuthenticationJSON>>() {}.type
        return responseParser(dispatcher, type) {
            service.validateMFACode(
                apikey = client.key,
                userToken = request.userToken,
                otpCode = request.otpCode,
                code = request.otpCode
            )
        }
    }
}