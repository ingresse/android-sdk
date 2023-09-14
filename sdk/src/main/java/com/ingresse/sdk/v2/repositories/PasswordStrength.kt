package com.ingresse.sdk.v2.repositories

import com.google.gson.reflect.TypeToken
import com.ingresse.sdk.IngresseClient
import com.ingresse.sdk.builders.ClientBuilder
import com.ingresse.sdk.builders.Host
import com.ingresse.sdk.builders.URLBuilder
import com.ingresse.sdk.v2.models.base.IngresseResponse
import com.ingresse.sdk.v2.models.request.ValidateStrength
import com.ingresse.sdk.v2.models.response.PasswordStrengthJSON
import com.ingresse.sdk.v2.parses.model.Result
import com.ingresse.sdk.v2.parses.responseParser
import com.ingresse.sdk.v2.services.PasswordStrengthService
import kotlinx.coroutines.CoroutineDispatcher
import retrofit2.Retrofit
import retrofit2.converter.scalars.ScalarsConverterFactory

class PasswordStrength(private val client: IngresseClient) {

    private val service: PasswordStrengthService

    init {
        val httpClient = ClientBuilder(client)
            .addRequestHeaders()
            .build()

        val adapter = Retrofit.Builder()
            .client(httpClient)
            .addConverterFactory(ScalarsConverterFactory.create())
            .baseUrl(URLBuilder(Host.API, client.environment, client.customPrefix).build())
            .build()

        service = adapter.create(PasswordStrengthService::class.java)
    }

    suspend fun validateStrength(
        dispatcher: CoroutineDispatcher,
        request: ValidateStrength,
    ): Result<IngresseResponse<PasswordStrengthJSON>> {
        val type = object : TypeToken<IngresseResponse<PasswordStrengthJSON>>() {}.type
        return responseParser(dispatcher, type) {
            service.validatePasswordStrength(
                apikey = client.key,
                password = request.password
            )
        }
    }
}
