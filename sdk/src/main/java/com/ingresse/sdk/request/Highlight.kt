package com.ingresse.sdk.request

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface Highlight {
    /**
     * Get event list by producer
     *
     * @param title - Event's title to filter
     * @param size - Size of total results in page
     * @param orderBy - Order results
     * @param from - Get from specific date or moment
     * @param to - Get to specific date or moment
     * @param offset - Get from specific page
     */
    @GET("/search/producer")
    fun getHighlightEvents(@Query("apikey") apikey: String,
                           @Query("state") state: String? = null,
                           @Query("page") page: Int,
                           @Query("pageSize") pageSize: Int): Call<String>
}