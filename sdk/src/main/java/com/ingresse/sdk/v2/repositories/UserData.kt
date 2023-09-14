package com.ingresse.sdk.v2.repositories

import com.google.gson.reflect.TypeToken
import com.ingresse.sdk.IngresseClient
import com.ingresse.sdk.builders.ClientBuilder
import com.ingresse.sdk.builders.Host
import com.ingresse.sdk.builders.URLBuilder
import com.ingresse.sdk.v2.models.base.IngresseResponse
import com.ingresse.sdk.v2.models.request.CreateUser
import com.ingresse.sdk.v2.models.request.UpdateUserData
import com.ingresse.sdk.v2.models.request.UserData
import com.ingresse.sdk.v2.models.response.CreateUserJSON
import com.ingresse.sdk.v2.models.response.GetUserJSON
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
            .addTimeout(TIMEOUT)
            .build()

        val adapter = Retrofit.Builder()
            .client(httpClient)
            .addConverterFactory(ScalarsConverterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(URLBuilder(Host.API, client.environment, client.customPrefix).build())
            .build()

        service = adapter.create(UserDataService::class.java)
    }

    suspend fun getUserData(
        dispatcher: CoroutineDispatcher = Dispatchers.Default,
        request: UserData,
    ): Result<IngresseResponse<GetUserJSON>> {
        val fields = listOf(
            "id", "name", "lastname",
            "document", "email", "zip", "number",
            "complement", "city", "state", "street",
            "district", "ddi", "phone", "verified", "fbUserId",
            "type", "pictures", "picture", "planner", "birthdate"
        )

        val customFields = request.fields ?: fields.joinToString(",")

        val type = object : TypeToken<IngresseResponse<GetUserJSON>>() {}.type
        return responseParser(dispatcher, type) {
            service.getUserData(
                userId = request.userId,
                apikey = client.key,
                userToken = request.userToken,
                fields = customFields
            )
        }
    }

    suspend fun updateUserData(
        dispatcher: CoroutineDispatcher = Dispatchers.Default,
        request: UpdateUserData
    ): Result<Void> {
        val type = object : TypeToken<Void>() {}.type
        return responseParser(dispatcher, type) {
            service.updateUserData(
                userId = request.userId,
                apikey = client.key,
                userToken = request.userToken,
                params = request.params
            )
        }
    }

    suspend fun createUser(
        dispatcher: CoroutineDispatcher = Dispatchers.Default,
        request: CreateUser
    ): Result<IngresseResponse<CreateUserJSON>> {
        val type = object : TypeToken<IngresseResponse<CreateUserJSON>>() {}.type
        return responseParser(dispatcher, type) {
            service.createUser(
                apikey = client.key,
                params = request
            )
        }
    }

    companion object {

        const val TIMEOUT = 60L
    }
}
