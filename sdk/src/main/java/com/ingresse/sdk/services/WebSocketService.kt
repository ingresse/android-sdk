package com.ingresse.sdk.services

import android.net.Uri
import com.google.gson.reflect.TypeToken
import com.ingresse.sdk.IngresseClient
import com.ingresse.sdk.base.IngresseSocketCallback
import com.ingresse.sdk.base.SocketObserver
import com.ingresse.sdk.base.SocketResponse
import com.ingresse.sdk.builders.Host
import com.ingresse.sdk.builders.URLBuilder
import com.ingresse.sdk.model.response.WebSocketTransactionDataJSON
import org.phoenixframework.socket.Socket

private typealias WebSocketTransactionResponse = SocketResponse<WebSocketTransactionDataJSON>
class WebSocketService(private val client: IngresseClient) {
    private var host = Host.API
    private var socket: Socket? = null

    /**
     * Method to cancel websocket get transaction
     */
    fun closeConnection() = socket?.disconnect()

    /**
     * Get Transaction
     *
     * @param transactionId - id from transaction
     * @param onSuccess - success callback
     */
    fun getTransaction(transactionId: String, onSuccess: (transactionId: String, status: String) -> Unit ) {
        val url = URLBuilder(host, client.environment, client.customPrefix)
            .addPath("/websocket")
            .addParameter("token", client.authToken)
            .socketBuild()

        socket = Socket(endpointUri = Uri.parse(url).buildUpon().toString())

        val topic = "transaction:$transactionId"
        val type = object: TypeToken<WebSocketTransactionResponse>() {}.type
        val observer = SocketObserver(topic, type, object: IngresseSocketCallback<WebSocketTransactionResponse> {
            override fun onError(error: Throwable?) {}
            override fun onClosing(code: Int?, reason: String?) {}
            override fun onClosed(code: Int?, reason: String?) {}
            override fun onOpen() {}
            override fun onDataReceived(data: WebSocketTransactionResponse?) {
                val transaction = data?.payload?.data ?: return
                onSuccess(transaction.transactionId.orEmpty(), transaction.status.orEmpty())
            }
        })

        socket?.registerEventListener(observer)
        socket?.connect()
    }
}