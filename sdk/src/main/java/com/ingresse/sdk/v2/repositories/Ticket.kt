package com.ingresse.sdk.v2.repositories

import com.google.gson.reflect.TypeToken
import com.ingresse.sdk.IngresseClient
import com.ingresse.sdk.builders.ClientBuilder
import com.ingresse.sdk.builders.Host
import com.ingresse.sdk.builders.URLBuilder
import com.ingresse.sdk.v2.models.base.IngresseResponse
import com.ingresse.sdk.v2.models.request.EventTickets
import com.ingresse.sdk.v2.models.response.EventTicketJSON
import com.ingresse.sdk.v2.parses.model.Result
import com.ingresse.sdk.v2.parses.responseParser
import com.ingresse.sdk.v2.services.TicketService
import kotlinx.coroutines.CoroutineDispatcher
import retrofit2.Retrofit
import retrofit2.converter.scalars.ScalarsConverterFactory

class Ticket(private val client: IngresseClient) {

    private val service: TicketService

    init {
        val httpClient = ClientBuilder(client)
            .addRequestHeaders()
            .build()

        val adapter = Retrofit.Builder()
            .client(httpClient)
            .addConverterFactory(ScalarsConverterFactory.create())
            .baseUrl(URLBuilder(Host.API, client.environment).build())
            .build()

        service = adapter.create(TicketService::class.java)
    }

    suspend fun getEventTickets(
        dispatcher: CoroutineDispatcher,
        request: EventTickets,
    ): Result<IngresseResponse<List<EventTicketJSON>>> {
        val type = object : TypeToken<IngresseResponse<List<EventTicketJSON>>>() {}.type
        return responseParser(dispatcher, type) {
            service.getEventTickets(
                apikey = client.key,
                eventId = request.eventId,
                sessionId = request.sessionId
            )
        }
    }
}
