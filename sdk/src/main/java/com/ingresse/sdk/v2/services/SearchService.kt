package com.ingresse.sdk.v2.services

import com.ingresse.sdk.v2.models.base.Data
import com.ingresse.sdk.v2.models.response.searchEvents.SearchEventsJSON
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface SearchService {
    /**
     * Get information of some event by searching it's title
     *
     * @param title - Event's title as search term
     * @param state - Event's state to search
     * @param category - Event's category to search
     * @param term - Event's specific term to search
     * @param size - Size of total results in page
     * @param from - Get from specific date or moment
     * @param orderBy - Order results
     * @param offset - Get from specific page
     */
    @GET("{company}")
    suspend fun getEvents(
        @Path("company") company: String,
        @Query("title") title: String?,
        @Query("state") state: String?,
        @Query("category") category: String?,
        @Query("term") term: String?,
        @Query("size") size: Int?,
        @Query("from") from: String?,
        @Query("to") to: String?,
        @Query("orderBy") orderBy: String?,
        @Query("offset") offset: Int
    ): Data<SearchEventsJSON>
}
