package com.ingresse.sdk.v2.services

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface TicketService {

    /**
     * Get event ticket list
     *
     * @param eventId - id from selected event
     * @param sessionId - id from selected session
     */
    @GET("/event/{eventId}/session/{sessionId}/tickets")
    suspend fun getEventTickets(
        @Path("eventId") eventId: Int,
        @Path("sessionId") sessionId: String,
        @Query("apikey") apikey: String,
    ): Response<String>
}
