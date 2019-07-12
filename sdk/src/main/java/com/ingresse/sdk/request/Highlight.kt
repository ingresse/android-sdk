package com.ingresse.sdk.request

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface Highlight {
    /**
     * Get highlight events
     *
     * @param state - Event's state to filter
     * @param pageSize - Size of total results in page
     * @param page - search page
     */
    @GET("/featured")
    fun getHighlightEvents(@Query("apikey") apikey: String,
                           @Query("state") state: String? = null,
                           @Query("page") page: Int,
                           @Query("pageSize") pageSize: Int): Call<String>
}