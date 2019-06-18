package com.ingresse.sdk.request

import com.ingresse.sdk.model.request.SellTickets
import retrofit2.Call
import retrofit2.http.*

interface POS {

    /**
     * Sell tickets
     *
     * @param userToken - token from user
     * @param method - api method to sell
     */
    @POST("/ticketbooth")
    fun sellTickets(@Query("apikey") apikey: String,
                    @Query("usertoken") userToken: String,
                    @Query("method") method: String = "sell",
                    @Body params: SellTickets): Call<String>

    /**
     * Print tickets
     *
     * @param transactionId - id from transaction
     * @param userToken - token from user
     * @param method - api method to print
     */
    @GET("/ticketbooth/{transactionId}")
    fun printTickets(@Path("transactionId") transactionId: String,
                     @Query("apikey") apikey: String,
                     @Query("usertoken") userToken: String,
                     @Query("method") method: String = "print"): Call<String>
}