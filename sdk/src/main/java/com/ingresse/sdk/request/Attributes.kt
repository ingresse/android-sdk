package com.ingresse.sdk.request

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface Attributes {

    /**
     * Get event attributes
     *
     * @param eventId - id from event
     * @param filters - specific fields from event attributes
     */
    @GET("/event/{eventId}/attributes")
    fun getEventAttributes(@Path("eventId") eventId: String,
                           @Query("apikey") apikey: String,
                           @Query("filters") filters: String): Call<String>

    /**
     * Get planner attributes
     *
     * @param eventId - id from event
     * @param fields - specific attributes from planner
     */
    @GET("/event/{eventId}")
    fun getPlannerAttributes(@Path("eventId") eventId: String,
                             @Query("apikey") apikey: String,
                             @Query("fields") fields: String): Call<String>
}