package com.ingresse.sdk.v2.services

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface BackstageReportsService {

    /**
     * Get producer´s event list
     *
     * @param eventId - id from selected event
     * @param sessionId - id from selected session
     */
    @GET("/api/event/{eventId}/session/{sessionId}/report/entrance")
    suspend fun getEntranceReport(
        @Path("eventId") eventId: String,
        @Path("sessionId") sessionId: String,
        @Query("ticket_group_id") groupId: String?,
    ): Response<*>
}
