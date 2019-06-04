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
import com.ingresse.sdk.model.request.FriendsFromSearch
import com.ingresse.sdk.model.request.RecentTransfers
import com.ingresse.sdk.model.request.UserTransfersData
import com.ingresse.sdk.model.response.FriendsFromSearchJSON
import com.ingresse.sdk.model.response.RecentTransfersJSON
import com.ingresse.sdk.model.response.UserTransfersJSON
import com.ingresse.sdk.request.Transfer
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import java.io.IOException

class TransferService(private val client: IngresseClient) {
    private var host = Host.API
    private var service: Transfer
    private var cancelAllCalled = false

    private var mUserTransfersCall: Call<String>? = null
    private var mRecentTransfersCall: Call<String>? = null
    private var mFriendsFromSearchCall: Call<String>? = null
    private var mConcurrentCalls: ArrayList<Call<String>> = ArrayList()

    init {
        val httpClient = ClientBuilder(client)
            .addRequestHeaders()
            .build()

        val adapter = Retrofit.Builder()
            .addConverterFactory(ScalarsConverterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .client(httpClient)
            .baseUrl(URLBuilder(host, client.environment).build())
            .build()

        service = adapter.create(Transfer::class.java)
    }

    /**
     * Method to cancel all requests
     */
    fun cancelAll() {
        cancelAllCalled = true
        mUserTransfersCall?.cancel()
        mRecentTransfersCall?.cancel()
        mFriendsFromSearchCall?.cancel()
        mConcurrentCalls.forEach { it.cancel() }
        mConcurrentCalls.clear()
    }

    /**
     * Method to cancel user transfers data request
     */
    fun cancelUserTransfersData(concurrent: Boolean = false) {
        if(!concurrent) {
            mUserTransfersCall?.cancel()
            return
        }

        mConcurrentCalls.forEach { it.cancel() }
        mConcurrentCalls.clear()
    }

    /**
     * Method to cancel recent transfers data request
     */
    fun cancelRecentTransfersData(concurrent: Boolean = false) {
        if(!concurrent) {
            mRecentTransfersCall?.cancel()
            return
        }

        mConcurrentCalls.forEach { it.cancel() }
        mConcurrentCalls.clear()
    }

    /**
     * Method to cancel a get friends from search
     */
    fun cancelFriendsFromSearch(concurrent: Boolean = false) {
        if (!concurrent) {
            mFriendsFromSearchCall?.cancel()
            return
        }

        mConcurrentCalls.forEach { it.cancel() }
        mConcurrentCalls.clear()
    }

    /**
     * Get user transfers data
     *
     * @param concurrent - parameters to concurrent request
     * @param request - parameters required to request
     * @param onSuccess - success callback
     * @param onError - error callback
     * @param onConnectionError - connection error callback
     */
    fun getUserTransfersData(concurrent: Boolean = false,
                             request: UserTransfersData,
                             onSuccess: (com.ingresse.sdk.base.Array<UserTransfersJSON>) -> Unit,
                             onError: (APIError) -> Unit,
                             onConnectionError: (error: Throwable) -> Unit) {

        var call = service.getUserTransfers(
            userId = request.userId,
            apikey = client.key,
            page = request.page,
            pageSize = request.pageSize,
            token = request.usertoken,
            status = request.status
        )

        if (!concurrent) mUserTransfersCall = call else mConcurrentCalls.add(call)

        val callback = object: IngresseCallback<Response<com.ingresse.sdk.base.Array<UserTransfersJSON>>?> {
            override fun onSuccess(data: Response<com.ingresse.sdk.base.Array<UserTransfersJSON>>?) {
                val response = data?.responseData ?: return onError(APIError.default)

                if(!concurrent) mUserTransfersCall = null else mConcurrentCalls.remove(call)
                onSuccess(response)
            }

            override fun onError(error: APIError) {
                if (!concurrent) mUserTransfersCall = null else mConcurrentCalls.remove(call)
                onError(error)
            }

            override fun onRetrofitError(error: Throwable) {
                if (!concurrent) mUserTransfersCall = null else mConcurrentCalls.remove(call)
                if (error is IOException) return onConnectionError(error)

                val apiError = APIError()
                apiError.message = error.localizedMessage
                onError(apiError)
            }
        }

        val type = object : TypeToken<Response<com.ingresse.sdk.base.Array<UserTransfersJSON>>?>() {}.type
        call.enqueue(RetrofitCallback(type, callback))
    }

    /**
     * Gets user recent transfers data
     *
     * @param concurrent - parameters to concurrent request
     * @param request - parameters required to request
     * @param onSuccess - success callback
     * @param onError - error callback
     * @param onConnectionError - connection error callback
     */
    fun getRecentTransfersData(concurrent: Boolean = false,
                               request: RecentTransfers,
                               onSuccess: (List<RecentTransfersJSON>) -> Unit,
                               onError: (APIError) -> Unit,
                               onConnectionError: (error: Throwable) -> Unit) {

        var call = service.getRecentTransfers(
            userId = request.userId,
            apikey = client.key,
            userToken = request.usertoken,
            order = request.order,
            size = request.size
        )

        if (!concurrent) mRecentTransfersCall = call else mConcurrentCalls.add(call)

        val callback = object: IngresseCallback<Response<List<RecentTransfersJSON>>?> {
            override fun onSuccess(data: Response<List<RecentTransfersJSON>>?) {
                val response = data?.responseData ?: return onError(APIError.default)

                if(!concurrent) mRecentTransfersCall = null else mConcurrentCalls.remove(call)
                onSuccess(response)
            }

            override fun onError(error: APIError) {
                if (!concurrent) mRecentTransfersCall = null else mConcurrentCalls.remove(call)
                onError(error)
            }

            override fun onRetrofitError(error: Throwable) {
                if (!concurrent) mRecentTransfersCall = null else mConcurrentCalls.remove(call)
                if (error is IOException) return onConnectionError(error)

                val apiError = APIError()
                apiError.message = error.localizedMessage
                onError(apiError)
            }
        }

        val type = object : TypeToken<Response<List<RecentTransfersJSON>>?>() {}.type
        call.enqueue(RetrofitCallback(type, callback))
    }

    /**
     * Search friends by name
     *
     * @param concurrent - parameters to concurrent request
     * @param request - parameters required to request
     * @param onSuccess - success callback
     * @param onError - error callback
     * @param onConnectionError - connection error callback
     */
    fun getFriendsFromSearch(concurrent: Boolean = false,
                             request: FriendsFromSearch,
                             onSuccess: (Array<FriendsFromSearchJSON>) -> Unit,
                             onError: (APIError) -> Unit,
                             onConnectionError: (error: Throwable) -> Unit) {

        var call = service.getFriendsFromSearch(
            term = request.term,
            size = request.size,
            apikey = client.key,
            userToken = request.usertoken
        )

        if (!concurrent) mFriendsFromSearchCall = call else mConcurrentCalls.add(call)

        val callback = object: IngresseCallback<Response<Array<FriendsFromSearchJSON>>?> {
            override fun onSuccess(data: Response<Array<FriendsFromSearchJSON>>?) {
                val response = data?.responseData ?: return onError(APIError.default)

                if (!concurrent) mFriendsFromSearchCall = null else mConcurrentCalls.remove(call)
                onSuccess(response)
            }

            override fun onError(error: APIError) {
                if (!concurrent) mFriendsFromSearchCall = null else mConcurrentCalls.remove(call)
                onError(error)
            }

            override fun onRetrofitError(error: Throwable) {
                if (!concurrent) mFriendsFromSearchCall = null else mConcurrentCalls.remove(call)
                if (error is IOException) return onConnectionError(error)

                val apiError = APIError()
                apiError.message = error.localizedMessage
                onError(apiError)
            }
        }

        val type = object : TypeToken<Response<Array<FriendsFromSearchJSON>>?>() {}.type
        call.enqueue(RetrofitCallback(type, callback))
    }
}