package com.ingresse.sdk.request

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface Search {
    /**
     * Get information of some event by searching it's title
     *
     * @param title - Event's title as search term
     * @param size - Size of total results in page
     * @param from - Get from specific date or moment
     * @param orderBy - Order results
     * @param offset - Get from specific page
     */
    @GET("1")
    fun getEventByTitle(@Query("title") title: String,
                        @Query("size") size: String,
                        @Query("from") from: String,
                        @Query("orderBy") orderBy: String,
                        @Query("offset") offset: String) : Call<String>

    /**
     * Search friends by name
     *
     * @param term - String term to search for
     * @param size - Number of results
     * @param userToken - User token
     */
    @GET("/search/transfer/user")
    fun getFriendsFromSearch(@Query("term") term: String,
                             @Query("size") size: String?,
                             @Query("usertoken") userToken: String): Call<String>

}