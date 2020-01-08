package com.ingresse.sdk.request

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface Sale {
    /**
     * Get Session tickets for POS
     *
     * @param eventId - Event id
     * @param sessionId - Session id
     * @param userToken - Token for logged user
     * @param passKey - Passkey code to show hidden tickets
     */
    @GET("/event/{eventId}/session/{sessionId}/tickets")
    fun getTicketList(@Path("eventId") eventId: String,
                      @Path("sessionId") sessionId: String,
                      @Query("hideSessions") hideSessions: Boolean,
                      @Query("date") dateToFilter: String?,
                      @Query("item") itemName: String?,
                      @Query("apikey") apikey: String,
                      @Query("passkey") passKey: String?,
                      @Query("page") page: Int,
                      @Query("pageSize") pageSize: Int,
                      @Query("pos") pos: Boolean?,
                      @Query("usertoken") userToken: String): Call<String>
}