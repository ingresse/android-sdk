package com.ingresse.sdk.request

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import retrofit2.http.QueryMap

interface History {
    /**
     * Get ticket transfer history
     *
     * @param ticketId  - Event id
     * @param userToken - user token required
     * @param callback - Callback action
     */
    @GET("/ticket/{ticketId}/transfer")
    fun getTransferHistory(@Path("ticketId") ticketId: String,
                           @Query("apikey") apikey: String,
                           @Query("usertoken") userToken: String) : Call<String>
}