package com.ingresse.sdk.v2.repositories

import com.google.gson.reflect.TypeToken
import com.ingresse.sdk.IngresseClient
import com.ingresse.sdk.builders.ClientBuilder
import com.ingresse.sdk.builders.Host
import com.ingresse.sdk.builders.URLBuilder
import com.ingresse.sdk.v2.models.base.IngresseResponse
import com.ingresse.sdk.v2.models.request.EventAttributes
import com.ingresse.sdk.v2.models.request.EventDetailsById
import com.ingresse.sdk.v2.models.request.EventDetailsByLink
import com.ingresse.sdk.v2.models.response.eventAttributes.EventAttributesJSON
import com.ingresse.sdk.v2.models.response.eventDetails.EventDetailsJSON
import com.ingresse.sdk.v2.parses.model.Result
import com.ingresse.sdk.v2.parses.responseParser
import com.ingresse.sdk.v2.services.EventDetailsService
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import retrofit2.Retrofit
import retrofit2.converter.scalars.ScalarsConverterFactory

class EventDetails(private val client: IngresseClient) {

    private val service: EventDetailsService

    init {
        val httpClient = ClientBuilder(client)
            .addRequestHeaders()
            .build()

        val adapter = Retrofit.Builder()
            .client(httpClient)
            .addConverterFactory(ScalarsConverterFactory.create())
            .baseUrl(URLBuilder(Host.API, client.environment).build())
            .build()

        service = adapter.create(EventDetailsService::class.java)
    }

    suspend fun getEventDetailsById(
        dispatcher: CoroutineDispatcher = Dispatchers.Default,
        request: EventDetailsById,
    ): Result<IngresseResponse<EventDetailsJSON>> {
        val type = object : TypeToken<IngresseResponse<EventDetailsJSON>>() {}.type
        return responseParser(dispatcher, type) {
            service.getEventDetailsById(
                eventId = request.eventId,
                apikey = client.key,
                fields = request.fields
            )
        }
    }

    suspend fun getEventDetailsByLink(
        dispatcher: CoroutineDispatcher = Dispatchers.Default,
        request: EventDetailsByLink,
    ): Result<IngresseResponse<EventDetailsJSON>> {
        val type = object : TypeToken<IngresseResponse<EventDetailsJSON>>() {}.type
        return responseParser(dispatcher, type) {
            service.getEventDetailsByLink(
                apikey = client.key,
                method = request.link,
                link = request.link,
                fields = request.fields
            )
        }
    }

    suspend fun getEventAttributes(
        dispatcher: CoroutineDispatcher = Dispatchers.Default,
        request: EventAttributes,
    ): Result<IngresseResponse<EventAttributesJSON>> {
        val type = object : TypeToken<IngresseResponse<EventAttributesJSON>>() {}.type
        return responseParser(dispatcher, type) {
            service.getEventAttributes(
                apikey = client.key,
                eventId = request.eventId
            )
        }
    }
}
