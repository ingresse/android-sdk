package com.ingresse.sdk.request

import retrofit2.Call
import retrofit2.http.*

interface Event {
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
    fun getEventListByProducer(@Query("title") title: String? = null,
                               @Query("size") size: Int? = null,
                               @Query("orderBy") orderBy: String? = null,
                               @Query("from") from: String? = null,
                               @Query("to") to: String? = null,
                               @Query("offset") offset: Int? = null): Call<String>
}