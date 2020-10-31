package com.ingresse.sdk.v2.services

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface EventDetailsService {
    /**
     * Get details from specified event by id
     *
     * @param eventId - id of event
     * @param fields - fields to get from details
     */
    @GET("/event/{eventId}")
    suspend fun getEventDetailsById(
        @Path("eventId") eventId: Int,
        @Query("apikey") apikey: String,
        @Query("fields") fields: String?,
    ): Response<String>

    /**
     * Get details from specified event by link
     *
     * @param method - type of identification
     * @param link - event link for details search
     * @param fields - fields to get from details
     */
    @GET("/event")
    suspend fun getEventDetailsByLink(
        @Query("apikey") apikey: String,
        @Query("method") method: String,
        @Query("link") link: String,
        @Query("fields") fields: String?,
    ): Response<String>

    /**
     * Get attributes from specific event
     *
     * @param eventId - id of event
     */
    @GET("/event/{eventId}/attributes")
    suspend fun getEventAttributes(
        @Path("eventId") eventId: Int,
        @Query("apikey") apikey: String
    ): Response<String>
}
