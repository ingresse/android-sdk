package com.ingresse.sdk.services

import com.ingresse.sdk.IngresseClient
import com.ingresse.sdk.builders.Environment.*
import com.ingresse.sdk.builders.Host
import com.ingresse.sdk.builders.URLBuilder
import com.ingresse.sdk.errors.APIError
import java.lang.Exception

class EventLiveService(private val client: IngresseClient) {

    /**
     * Get event live url
     *
     * @param apikey - api key
     * @param userToken - user token
     * @param liveEventId - id live event
     * @param onSuccess - success callback
     * @param onError - error callback
     */
    fun getEventLiveUrl(userToken: String,
                        ticketId : String,
                        onSuccess: (String) -> Unit,
                        onError: (APIError) -> Unit) {

        var liveUrl = URLBuilder(Host.LIVE, client.environment, client.customPrefix)
                .addParameter(key = "apikey", value = client.key)
                .addParameter(key = "userToken", value = userToken)
                .addParameter(key = "ticketCode", value = ticketId)

        if (client.environment != PROD) {
            val env = client.environment.prefix.replace("-", "")
            liveUrl = liveUrl.addParameter(key = "env", value = env)
        }

        return try {
            val request = liveUrl.paramsBuild()
            onSuccess(request)
        } catch (ex: Exception) {
            onError(APIError.default)
        }
    }

}