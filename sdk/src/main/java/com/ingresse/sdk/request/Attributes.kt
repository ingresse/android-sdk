package com.ingresse.sdk.request

import com.ingresse.sdk.model.request.AttributesToUpdate
import retrofit2.Call
import retrofit2.http.*

interface Attributes {

    /**
     * Get event attributes
     *
     * @param eventId - id from event
     * @param filters - specific fields from event attributes
     */
    @GET("/{eventId}/attributes")
    fun getEventAttributes(@Path("eventId") eventId: String,
                           @Query("apikey") apikey: String,
                           @Query("filters") filters: String?): Call<String>

    /**
     * Get event advertisement attribute
     *
     * @param eventId - id from event
     * @param userToken - token of logged user
     * @param filters - specific fields to advertisement attribute
     */
    @GET("/event/{eventId}/attributes")
    fun getEventAdvertisement(@Path("eventId") eventId: String,
                           @Query("apikey") apikey: String,
                           @Query("usertoken") userToken: String,
                           @Query("filters") filters: String?): Call<String>

    /**
     * Update event attributes
     *
     * @param eventId - id from event
     * @param userToken - token of logged user
     * @param body - attributes to update
     */
    @PUT("/{eventId}/attributes")
    fun updateEventAttributes(@Path("eventId") eventId: String,
                              @Query("apikey") apikey: String,
                              @Query("usertoken") userToken: String,
                              @Body body: AttributesToUpdate): Call<String>
}