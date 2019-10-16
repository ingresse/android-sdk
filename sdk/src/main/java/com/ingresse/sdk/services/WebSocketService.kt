package com.ingresse.sdk.services

import android.net.Uri
import com.google.gson.reflect.TypeToken
import com.ingresse.sdk.IngresseClient
import com.ingresse.sdk.base.IngresseSocketCallback
import com.ingresse.sdk.base.SocketObserver
import com.ingresse.sdk.base.SocketResponse
import com.ingresse.sdk.builders.Host
import com.ingresse.sdk.builders.URLBuilder
import org.phoenixframework.socket.Socket

class WebSocketService(private val client: IngresseClient) {
    private var host = Host.API
    private var socket: Socket? = null

    /**
     * Method to cancel sell tickets
     */
    fun closeConnection() = socket?.disconnect()

    /**
     * Sell tickets
     *
     * @param request - parameters required to request
     * @param onSuccess - success callback
     * @param onError - error callback
     */
    fun getTransaction(transactionId: String, onSuccess: (Pair<String, String>) -> Unit ) {
        if (client.authToken.isEmpty()) return
        val url = URLBuilder(host, client.environment)
            .addPath("/websocket")
            .addParameter("token", client.authToken)
            .socketBuild()

        socket = Socket(endpointUri = Uri.parse(url).buildUpon().toString())

        val type = object: TypeToken<SocketResponse<TransactionData>>() {}.type
        val observer = SocketObserver("transaction:$transactionId", type, object: IngresseSocketCallback<SocketResponse<TransactionData>> {
            override fun onError(error: Throwable?) {}
            override fun onClosing(code: Int?, reason: String?) {}
            override fun onClosed(code: Int?, reason: String?) {}
            override fun onOpen() {}
            override fun onDataReceived(data: SocketResponse<TransactionData>?) {
                val transaction = data?.payload?.data ?: return
                onSuccess(Pair(transaction.transactionId.orEmpty(), transaction.status.orEmpty()))
            }
        })

        socket?.registerEventListener(observer)
        socket?.connect()
    }
}

class TransactionData {
    val status: String? = null
    val transactionId : String? = null
}