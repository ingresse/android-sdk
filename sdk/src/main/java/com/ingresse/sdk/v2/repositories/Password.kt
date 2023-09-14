package com.ingresse.sdk.v2.repositories

import com.google.gson.reflect.TypeToken
import com.ingresse.sdk.IngresseClient
import com.ingresse.sdk.builders.ClientBuilder
import com.ingresse.sdk.builders.Host
import com.ingresse.sdk.builders.URLBuilder
import com.ingresse.sdk.v2.models.base.IngresseResponse
import com.ingresse.sdk.v2.models.request.RequestReset
import com.ingresse.sdk.v2.models.request.UpdatePassword
import com.ingresse.sdk.v2.models.request.ValidateHash
import com.ingresse.sdk.v2.models.response.password.PasswordResetJSON
import com.ingresse.sdk.v2.models.response.password.ValidateHashJSON
import com.ingresse.sdk.v2.parses.model.Result
import com.ingresse.sdk.v2.parses.responseParser
import com.ingresse.sdk.v2.services.PasswordService
import kotlinx.coroutines.CoroutineDispatcher
import retrofit2.Retrofit
import retrofit2.converter.scalars.ScalarsConverterFactory

class Password(private val client: IngresseClient) {

    private val service: PasswordService

    init {
        val httpClient = ClientBuilder(client)
            .addRequestHeaders()
            .build()

        val adapter = Retrofit.Builder()
            .client(httpClient)
            .addConverterFactory(ScalarsConverterFactory.create())
            .baseUrl(URLBuilder(Host.API, client.environment, client.customPrefix).build())
            .build()

        service = adapter.create(PasswordService::class.java)
    }

    suspend fun requestReset(
        dispatcher: CoroutineDispatcher,
        request: RequestReset,
    ): Result<IngresseResponse<PasswordResetJSON>> {
        val type = object : TypeToken<IngresseResponse<PasswordResetJSON>>() {}.type
        return responseParser(dispatcher, type) {
            service.requestReset(
                apikey = client.key,
                email = request.email
            )
        }
    }

    suspend fun validateHash(
        dispatcher: CoroutineDispatcher,
        request: ValidateHash,
    ): Result<IngresseResponse<ValidateHashJSON>> {
        val type = object : TypeToken<IngresseResponse<ValidateHashJSON>>() {}.type
        return responseParser(dispatcher, type) {
            service.validateHash(
                apikey = client.key,
                email = request.email,
                hash = request.hash
            )
        }
    }

    suspend fun updatePassword(
        dispatcher: CoroutineDispatcher,
        request: UpdatePassword,
    ): Result<IngresseResponse<PasswordResetJSON>> {
        val type = object : TypeToken<IngresseResponse<PasswordResetJSON>>() {}.type
        return responseParser(dispatcher, type) {
            service.updatePassword(
                apikey = client.key,
                email = request.email,
                password = request.password,
                hash = request.hash
            )
        }
    }
}
