package com.ingresse.sdk.request

import retrofit2.Call
import retrofit2.http.*

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

    /**
     * 2FA Authentication user device
     *
     * @param userToken - user token
     * @header X-INGRESSE-OTP - code to validate user device
     * @header X-INGRESSE-DEVICE - uuid user device data
     */
    @POST("/two-step")
    fun authenticationUserDevice(@Query("apikey") apikey: String,
                          @Query("usertoken") userToken: String,
                          @Header("X-INGRESSE-OTP") code: String? = null,
                          @Header("X-INGRESSE-DEVICE") uuid: String): Call<String>
}