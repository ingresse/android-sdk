package com.ingresse.sdk.request

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface Report {
    /**
     * Retrieve visits report
     *
     * @param eventId - id from event
     * @param userToken - user token
     * @param from - search from some date
     * @param to - search to some date
     */
    @GET("/dashboard/{eventId}/visitsReport")
    fun getVisitsReport(@Path("eventId") eventId: String,
                        @Query("apikey") apikey: String,
                        @Query("userToken") userToken: String,
                        @Query("from") from: String? = null,
                        @Query("to") to: String? = null): Call<String>
}