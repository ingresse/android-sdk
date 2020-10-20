package com.ingresse.sdk.v2.services

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
    fun <T> getEventDetailsById(
        @Path("eventId") eventId: String,
        @Query("apikey") apikey: String,
        @Query("fields") fields: String? = null
    ): T

    /**
     * Get details from specified event by link
     *
     * @param method - type of identification
     * @param link - event link for details search
     * @param fields - fields to get from details
     */
    @GET("/event")
    fun <T> getEventDetailsByLink(
        @Query("apikey") apikey: String,
        @Query("method") method: String,
        @Query("link") link: String,
        @Query("fields") fields: String? = null
    ): T
}