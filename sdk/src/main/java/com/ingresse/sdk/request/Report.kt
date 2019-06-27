package com.ingresse.sdk.request

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface Report {

    /**
     * Sell tickets
     *
     * @param userToken - token from user
     * @param method - api method to sell
     */
    @GET("/dashboard/{eventId}")
    fun getSessionDashboard(@Path("eventId") eventId: String,
                            @Query("apikey") apikey: String,
                            @Query("session") sessionId: String,
                            @Query("usertoken") userToken: String,
                            @Query("channel") channel: String? = null): Call<String>
}