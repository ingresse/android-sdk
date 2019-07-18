package com.ingresse.sdk.request

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface History {
    /**
     * Get ticket checkin history
     *
     * @param ticketCode - Ticket code (00.00000.?000000?.000000.00)
     * @param userToken - UserToken required
     */
    @GET("/ticket/{code}/status")
    fun getCheckinHistory(@Path("code") ticketCode: String,
                          @Query("apikey") apikey: String,
                          @Query("usertoken") userToken: String) : Call<String>
}