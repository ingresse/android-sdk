package com.ingresse.sdk.v2.repositories

import com.google.gson.GsonBuilder
import com.ingresse.sdk.IngresseClient
import com.ingresse.sdk.builders.ClientBuilder
import com.ingresse.sdk.builders.Host
import com.ingresse.sdk.builders.URLBuilder
import com.ingresse.sdk.v2.models.base.ResponseHits
import com.ingresse.sdk.v2.models.request.ProducerEventDetails
import com.ingresse.sdk.v2.models.request.SearchEvents
import com.ingresse.sdk.v2.models.response.searchEvents.SearchEventsJSON
import com.ingresse.sdk.v2.parses.model.Result
import com.ingresse.sdk.v2.parses.resultParser
import com.ingresse.sdk.v2.services.EventService
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class Event(client: IngresseClient) {
    private val service: EventService

    init {
        val httpClient = ClientBuilder(client)
            .addRequestHeaders()
            .build()

        val gsonBuilder = GsonBuilder().create()

        val adapter = Retrofit.Builder()
            .client(httpClient)
            .addConverterFactory(GsonConverterFactory.create(gsonBuilder))
            .baseUrl(URLBuilder(Host.SEARCH, client.environment).build())
            .build()

        service = adapter.create(EventService::class.java)
    }

    suspend fun getProducerEventListPlain(request: SearchEvents) =
        service.getProducerEventList(
            title = request.title,
            state = request.state,
            category = request.category,
            term = request.term,
            size = request.size,
            from = request.from,
            to = request.to,
            orderBy = request.orderBy,
            offset = request.offset
        )

    suspend fun getProducerEventList(
        dispatcher: CoroutineDispatcher = Dispatchers.Default,
        request: SearchEvents,
    ): Result<ResponseHits<SearchEventsJSON>> =
        resultParser(dispatcher) {
            getProducerEventListPlain(request)
        }

    suspend fun getProducerEventDetailsPlain(request: ProducerEventDetails) =
        service.getProducerEventDetails(
            eventId = request.eventId,
            fields = request.fields
        )

    suspend fun getProducerEventDetails(
        dispatcher: CoroutineDispatcher = Dispatchers.Default,
        request: ProducerEventDetails,
    ): Result<ResponseHits<SearchEventsJSON>> =
        resultParser(dispatcher) {
            getProducerEventDetailsPlain(request)
        }
}
