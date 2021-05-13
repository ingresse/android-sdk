package com.ingresse.sdk.v2.services

import com.ingresse.sdk.v2.models.response.ThresholdJSON
import retrofit2.http.GET
import retrofit2.http.Query

interface CheckinReportThresholdService {

    /**
     * Get threshold to access entrance report
     *
     * @param eventId - id from event
     */
    @GET("/prod/checkin_report_threshold")
    suspend fun getEntranceReportThreshold(
        @Query("event_id") eventId: Int,
    ): ThresholdJSON
}
