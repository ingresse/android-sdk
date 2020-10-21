package com.ingresse.sdk.v2.services

import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface UserWalletService {
    /**
     * Gets user tickets
     *
     * @param userId - id from user
     * @param token - token from user
     * @param page - current page
     * @param pageSize - size of response
     * @param eventId - specify event tickets by event id
     */
    @GET("/user/{userId}/tickets")
    fun <T> getUserTickets(
        @Path("userId") userId: Int,
        @Query("apikey") apikey: String,
        @Query("usertoken") token: String,
        @Query("page") page: Int?,
        @Query("pageSize") pageSize: Int?,
        @Query("eventId") eventId: Int?
    ): T
}
