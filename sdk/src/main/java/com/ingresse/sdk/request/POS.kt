package com.ingresse.sdk.request

import com.ingresse.sdk.model.request.SellTickets
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface POS {

    @POST("/ticketbooth")
    fun sellTickets(
        @Query("apikey") apikey: String,
        @Query("usertoken") userToken: String,
        @Query("method") method: String = "sell",
        @Body params: SellTickets
    ): Call<String>
}