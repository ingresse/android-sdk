package com.ingresse.sdk.v2.services

import retrofit2.Response
import retrofit2.http.GET

interface HomeService {

    /**
     *  Get all categories available to show in home app section
     */
    @GET("/home")
    suspend fun getHomeCategories(): Response<String>
}
