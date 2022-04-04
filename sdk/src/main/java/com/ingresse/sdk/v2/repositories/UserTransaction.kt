package com.ingresse.sdk.v2.repositories

import com.google.gson.reflect.TypeToken
import com.ingresse.sdk.IngresseClient
import com.ingresse.sdk.builders.ClientBuilder
import com.ingresse.sdk.builders.Host
import com.ingresse.sdk.builders.URLBuilder
import com.ingresse.sdk.v2.models.base.PagedResponse
import com.ingresse.sdk.v2.models.request.UserTransactionsRequest
import com.ingresse.sdk.v2.models.response.userTransactions.UserTransactionJSON
import com.ingresse.sdk.v2.parses.model.Result
import com.ingresse.sdk.v2.parses.responseParser
import com.ingresse.sdk.v2.services.UserTransactionService
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import retrofit2.Retrofit
import retrofit2.converter.scalars.ScalarsConverterFactory

class UserTransaction(private val client: IngresseClient) {
    private val service: UserTransactionService

    init {
        val httpClient = ClientBuilder(client)
            .addRequestHeaders()
            .build()

        val adapter = Retrofit.Builder()
            .client(httpClient)
            .addConverterFactory(ScalarsConverterFactory.create())
            .baseUrl(URLBuilder(Host.API, client.environment).build())
            .build()

        service = adapter.create(UserTransactionService::class.java)
    }

    suspend fun getUserTransactions(
        dispatcher: CoroutineDispatcher = Dispatchers.Default,
        request: UserTransactionsRequest
        ): Result<PagedResponse<UserTransactionJSON>> {
        val type = object : TypeToken<PagedResponse<UserTransactionJSON>>() {}.type
        return responseParser(dispatcher, type) {
            service.getUserTransactions(
                apikey = client.key,
                token = request.usertoken,
                status = request.status,
                page = request.page,
                pageSize = request.pageSize
            )
        }
    }
}