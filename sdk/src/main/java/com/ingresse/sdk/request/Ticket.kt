package com.ingresse.sdk.request

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface Ticket {
    /**
     * Get Event Tickets
     *
     * @param eventId - id from selected event
     * @param sessionId - id from selected session
     */
    @GET("/event/{eventId}/session/{sessionId}/tickets")
    fun getEventTickets(@Path("eventId") eventId: String,
                        @Path("sessionId") sessionId: String,
                        @Query("apikey") apikey: String): Call<String>
}