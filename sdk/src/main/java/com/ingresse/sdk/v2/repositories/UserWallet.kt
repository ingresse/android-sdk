package com.ingresse.sdk.v2.repositories

import com.google.gson.reflect.TypeToken
import com.ingresse.sdk.IngresseClient
import com.ingresse.sdk.builders.ClientBuilder
import com.ingresse.sdk.builders.Host
import com.ingresse.sdk.builders.URLBuilder
import com.ingresse.sdk.v2.models.base.IngresseResponse
import com.ingresse.sdk.v2.models.base.ResponseData
import com.ingresse.sdk.v2.models.request.UserTickets
import com.ingresse.sdk.v2.models.response.userWallet.UserTicketJSON
import com.ingresse.sdk.v2.parses.model.Result
import com.ingresse.sdk.v2.parses.responseParser
import com.ingresse.sdk.v2.services.UserWalletService
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import retrofit2.Retrofit
import retrofit2.converter.scalars.ScalarsConverterFactory

class UserWallet(private val client: IngresseClient) {
    private val service: UserWalletService

    init {
        val httpClient = ClientBuilder(client)
            .addRequestHeaders()
            .build()

        val adapter = Retrofit.Builder()
            .client(httpClient)
            .addConverterFactory(ScalarsConverterFactory.create())
            .baseUrl(URLBuilder(Host.API, client.environment).build())
            .build()

        service = adapter.create(UserWalletService::class.java)
    }

    suspend fun getUserTickets(
        dispatcher: CoroutineDispatcher = Dispatchers.Default,
        request: UserTickets,
    ): Result<IngresseResponse<ResponseData<UserTicketJSON>>> {
        val type = object : TypeToken<IngresseResponse<ResponseData<UserTicketJSON>>>() {}.type
        return responseParser(dispatcher, type) {
            service.getUserTickets(
                userId = request.userId,
                apikey = client.key,
                token = request.userToken,
                page = request.page,
                pageSize = request.pageSize,
                eventId = request.eventId
            )
        }
    }
}
