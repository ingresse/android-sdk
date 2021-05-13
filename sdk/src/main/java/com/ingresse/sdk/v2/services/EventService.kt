package com.ingresse.sdk.v2.services

import com.ingresse.sdk.v2.models.base.RegularData
import com.ingresse.sdk.v2.models.base.ResponseHits
import com.ingresse.sdk.v2.models.request.UpdateAttributes
import com.ingresse.sdk.v2.models.response.eventAttributes.UnspecifiedTypeJSON
import com.ingresse.sdk.v2.models.response.searchEvents.SearchEventsJSON
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.PUT
import retrofit2.http.Path
import retrofit2.http.Query

interface EventService {

    /**
     * Get producer´s event list
     *
     * @param title - Event's title to filter
     * @param size - Size of total results in page
     * @param orderBy - Order results
     * @param from - Get from specific date or moment
     * @param to - Get to specific date or moment
     * @param offset - Get from specific page
     */
    @Suppress("LongParameterList")
    @GET("/search/producer")
    suspend fun getProducerEventList(
        @Query("title") title: String?,
        @Query("state") state: String?,
        @Query("category") category: String?,
        @Query("term") term: String?,
        @Query("size") size: Int?,
        @Query("from") from: String?,
        @Query("to") to: String?,
        @Query("orderBy") orderBy: String?,
        @Query("offset") offset: Int,
    ): ResponseHits<SearchEventsJSON>

    /**
     * Get event details
     *
     * @param eventId - Id from event
     * @param fields - Get specifically described fields
     */
    @GET("/{eventId}")
    suspend fun getProducerEventDetails(
        @Path("eventId") eventId: Int,
        @Query("fields") fields: String?,
    ): ResponseHits<SearchEventsJSON>

    /**
     * Update specific event attributes
     *
     * @param eventId - Id from event
     * @param attributes - list of attributes to update
     */
    @PUT("/{eventId}/attributes")
    suspend fun updateEventAttributes(
        @Path("eventId") eventId: Int,
        @Body attributes: UpdateAttributes.Attributes,
    ): RegularData<String>
}
