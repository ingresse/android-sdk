package com.ingresse.sdk.request

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface EventDetails {
    /**
     * Get event details
     *
     * @param eventId - id of event to get details
     * @param fields - fields to get from details
     */
    @GET("/event/{eventId}")
    fun getEventDetails(@Path("eventId") eventId: String,
                        @Query("apikey") apikey: String,
                        @Query("fields") fields: String? = null): Call<String>
}