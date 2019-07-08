package com.ingresse.sdk.request

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface Report {

    /**
     * Get Session Dashboard
     *
     * @param eventId - id from event
     * @param sessionId - id from session
     * @param userToken - token from user
     * @param channel - dashboard channel (offline/online)
     */
    @GET("/dashboard/{eventId}")
    fun getSessionDashboard(@Path("eventId") eventId: String,
                            @Query("apikey") apikey: String,
                            @Query("session") sessionId: String,
                            @Query("usertoken") userToken: String,
                            @Query("channel") channel: String? = null): Call<String>

    /**
     * Get Sales Timeline
     *
     * @param eventId - id from event
     * @param sessionId - id from session
     * @param userToken - token from user
     * @param from - init timeline date
     * @param to - end timeline date
     * @param channel - dashboard channel (offline/online)
     */
    @GET("/dashboard/{eventId}/timeline")
    fun getSalesTimeline(@Path("eventId") eventId: String,
                         @Query("apikey") apikey: String,
                         @Query("session") sessionId: String,
                         @Query("usertoken") userToken: String,
                         @Query("from") from: String,
                         @Query("to") to: String,
                         @Query("channel") channel: String? = null): Call<String>
}