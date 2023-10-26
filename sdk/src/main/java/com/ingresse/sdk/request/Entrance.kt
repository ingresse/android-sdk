package com.ingresse.sdk.request

import com.ingresse.sdk.model.request.CheckinRequest
import io.reactivex.Single
import retrofit2.Call
import retrofit2.http.*

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
                          @Query("approved") approved: Int?,
                          @Query("page") page: Int,
                          @Query("pageSize") pageSize: Int,
                          @Query("from") dateFrom: Long?): Call<String>

    /**
     * Get ticket checkin history
     *
     * @param code - Ticket code (00.00000.?000000?.000000.00)
     * @param token - UserToken required
     */
    @GET("/ticket/{code}/status")
    fun getCheckinStatus(@Path("code") code: String,
                         @Query("apikey") apikey: String,
                         @Query("usertoken") token: String): Call<String>

    /**
     * Update ticket status
     *
     * @param eventId - Event id
     * @param userToken - Token for logged user
     */
    @Headers("ContentType: application/json")
    @POST("/event/{eventId}/guestlist?method=updatestatus")
    fun checkin(@Path("eventId") eventId: String,
                @Query("apikey") apiKey: String,
                @Query("usertoken") userToken: String,
                @Body request: CheckinRequest): Single<String>
}
