package com.ingresse.sdk.v2.services

import com.ingresse.sdk.v2.defaults.INITIAL_PAGE
import com.ingresse.sdk.v2.defaults.PAGE_SIZE
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface HighlightsService {
    /**
     * Get highlight events
     *
     * @param state - Event's state to filter
     * @param method - Choose events list with banner format
     * @param pageSize - Size of total results in page
     * @param page - search page
     */
    @GET("/featured")
    suspend fun getHighlights(
        @Query("apikey") apikey: String,
        @Query("state") state: String? = null,
        @Query("method") method: String,
        @Query("page") page: Int = INITIAL_PAGE,
        @Query("pageSize") pageSize: Int = PAGE_SIZE
    ): Response<String>
}
