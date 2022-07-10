package com.ingresse.sdk.v2.repositories

import com.google.gson.reflect.TypeToken
import com.ingresse.sdk.IngresseClient
import com.ingresse.sdk.builders.ClientBuilder
import com.ingresse.sdk.builders.Host
import com.ingresse.sdk.builders.URLBuilder
import com.ingresse.sdk.v2.models.base.IngresseResponse
import com.ingresse.sdk.v2.models.request.UpdateUserData
import com.ingresse.sdk.v2.models.request.UserData
import com.ingresse.sdk.v2.models.response.UpdateUserDataJSON
import com.ingresse.sdk.v2.models.response.UserDataJSON
import com.ingresse.sdk.v2.parses.model.Result
import com.ingresse.sdk.v2.parses.responseParser
import com.ingresse.sdk.v2.services.UserDataService
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory

class UserData(private val client: IngresseClient) {
    private val service: UserDataService

    init {
        val httpClient = ClientBuilder(client)
            .addRequestHeaders()
            .build()

        val adapter = Retrofit.Builder()
            .client(httpClient)
            .addConverterFactory(ScalarsConverterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(URLBuilder(Host.API, client.environment).build())
            .build()

        service = adapter.create(UserDataService::class.java)
    }

    suspend fun getUserData(
        dispatcher: CoroutineDispatcher = Dispatchers.Default,
        request: UserData,
    ): Result<IngresseResponse<UserDataJSON>> {
        val type = object : TypeToken<IngresseResponse<UserDataJSON>>() {}.type
        return responseParser(dispatcher, type) {
            service.getUserData(
                userId = request.userId,
                apikey = client.key,
                userToken = request.userToken,
                fields = request.fields
            )
        }
    }

    suspend fun updateUserData(
        dispatcher: CoroutineDispatcher = Dispatchers.Default,
        request: UpdateUserData
    ): Result<IngresseResponse<UpdateUserDataJSON>> {
        val type = object : TypeToken<IngresseResponse<UpdateUserDataJSON>>() {}.type
        return responseParser(dispatcher, type) {
            service.updateUserData(
                userId = request.userId,
                apikey = client.key,
                userToken = request.userToken,
                params = request
            )
        }
    }
}
