package com.ingresse.sdk.services

import com.google.gson.reflect.TypeToken
import com.ingresse.sdk.IngresseClient
import com.ingresse.sdk.base.IngresseCallback
import com.ingresse.sdk.base.Response
import com.ingresse.sdk.base.RetrofitCallback
import com.ingresse.sdk.builders.ClientBuilder
import com.ingresse.sdk.builders.Host
import com.ingresse.sdk.builders.URLBuilder
import com.ingresse.sdk.errors.APIError
import com.ingresse.sdk.model.request.SalesGroup
import com.ingresse.sdk.model.response.SalesGroupJSON
import com.ingresse.sdk.request.Permission
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.scalars.ScalarsConverterFactory

class PermissionService(private val client: IngresseClient) {
    private var host = Host.API
    private var service: Permission

    private var mSalesGroupCall: Call<String>? = null

    init {
        val httpClient = ClientBuilder(client)
            .addRequestHeaders()
            .build()

        val adapter = Retrofit.Builder()
                .addConverterFactory(ScalarsConverterFactory.create())
                .client(httpClient)
                .baseUrl(URLBuilder(host, client.environment).build())
                .build()

        service = adapter.create(Permission::class.java)
    }

    /**
     * Method to cancel sales group call
     */
    fun cancelSalesGroup() = mSalesGroupCall?.cancel()

    /**
     * Method to get sales group from user
     *
     * @param request - all parameters used for retrieve sales group
     * @param onSuccess - success callback
     * @param onError - error callback
     */
    fun getSalesGroup(request: SalesGroup, onSuccess: (Array<SalesGroupJSON>) -> Unit, onError: (APIError) -> Unit) {
        if (client.authToken.isEmpty()) return onError(APIError.default)

        mSalesGroupCall = service.getSalesGroup(
                apikey = client.key,
                userToken = request.userToken
        )

        val callback = object : IngresseCallback<Response<Array<SalesGroupJSON>?>> {
            override fun onSuccess(data: Response<Array<SalesGroupJSON>?>?) {
                val response = data?.responseData ?: return onError(APIError.default)
                onSuccess(response)
            }

            override fun onError(error: APIError) = onError(error)

            override fun onRetrofitError(error: Throwable) {
                val apiError = APIError()
                apiError.message = error.localizedMessage
                onError(apiError)
            }
        }

        val type = object : TypeToken<Response<Array<SalesGroup>?>>() {}.type
        mSalesGroupCall?.enqueue(RetrofitCallback(type, callback))
    }
}