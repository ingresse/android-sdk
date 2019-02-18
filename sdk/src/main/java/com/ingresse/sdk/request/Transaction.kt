package com.ingresse.sdk.request

import retrofit2.Call
import retrofit2.http.*

interface Transaction {
    /**
     * Create transaction to get id for payment or reserve
     *
     * @param userToken - user token
     * @param userId - user id
     * @param eventId - event id
     * @param passkey - passkey
     * @param tickets - all tickets for transaction
     */
    @POST("/shop")
    fun createTransaction(@Query("usertoken") userToken: String,
                          @Query("userId") userId: String,
                          @Query("eventId") eventId: String,
                          @Query("passkey") passkey: String?,
                          @QueryMap tickets: Map<String, String>) : Call<String>

    /**
     * Get transaction details
     *
     * @param transactionId - transaction id
     * @param userToken - user token
     */
    @GET("/sale/{transactionId}")
    fun getTransactionDetails(@Path("transactionId") transactionId: String,
                              @Query("usertoken") userToken: String) : Call<String>

    /**
     * Get status from user tickets
     *
     * @param ticketCode - ticket code
     * @param userToken - user token
     */
    @GET("/ticket/{ticketCode}/status")
    fun getCheckinStatus(@Path("ticketCode") ticketCode: String,
                         @Query("userToken") userToken: String) : Call<String>
}