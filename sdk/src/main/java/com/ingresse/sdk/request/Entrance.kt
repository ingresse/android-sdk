package com.ingresse.sdk.request

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface Entrance {

    /**
     * Get event Guest List
     *
     * @param eventId - Event id
     * @param sessionId - Session id
     * @param userToken - Token for logged user
     * @param page - Response page to query
     * @param pageSize - Size of the response's pages
     * @param dateFrom - Size of the response's pages
     */
    @GET("/event/{eventid}/guestlist")
    fun getEventGuestList(@Path("eventid") eventId: String,
                          @Query("apikey") apikey: String,
                          @Query("sessionid") sessionId: String,
                          @Query("usertoken") userToken: String,
                          @Query("page") page: Int,
                          @Query("pageSize") pageSize: Int,
                          @Query("from") dateFrom: Long?) : Call<String>
}
