package com.ingresse.sdk.request

import com.ingresse.sdk.model.request.SellTickets
import retrofit2.Call
import retrofit2.http.*

interface POS {

    @POST("/ticketbooth")
    fun sellTickets(@Query("apikey") apikey: String,
                    @Query("usertoken") userToken: String,
                    @Query("method") method: String = "sell",
                    @Body params: SellTickets): Call<String>

    @GET("/ticketbooth/{transactionId}")
    fun printTickets(@Path("transactionId") transactionId: String,
                     @Query("apikey") apikey: String,
                     @Query("usertoken") userToken: String,
                     @Query("method") method: String = "print"): Call<String>

    @GET("/event/{eventId}")
    fun getPlannerAttributes(@Path("eventId") eventId: String,
                             @Query("apikey") apikey: String,
                             @Query("fields") fields: String): Call<String>
}