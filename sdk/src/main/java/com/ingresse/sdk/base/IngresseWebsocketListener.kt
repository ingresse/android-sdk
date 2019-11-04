package com.ingresse.sdk.base

import com.google.gson.Gson
import org.phoenixframework.Message
import org.phoenixframework.socket.PhoenixSocketEventListener
import org.phoenixframework.socket.Socket
import java.lang.reflect.Type

class SocketObserver<T>(private val topic: String, val type: Type, val callback: IngresseSocketCallback<T>): PhoenixSocketEventListener {
    override fun onClosing(socket: Socket, code: Int?, reason: String?) = callback.onClosing(code, reason)
    override fun onClosed(socket: Socket, code: Int?, reason: String?) = callback.onClosed(code, reason)
    override fun onFailure(socket: Socket, t: Throwable?) = callback.onError(t)

    override fun onOpen(socket: Socket) {
        callback.onOpen()
        with(socket.channel(topic = topic)) {
            join(payload = null,
                success = { message: Message? -> message?.event },
                failure = { message: Message?, throwable: Throwable? ->
                    System.out.println("Join Error: ${message?.toString()} ${throwable?.message}")
                })
        }
    }

    override fun onMessage(socket: Socket, text: String?) {
        val gson = Gson()
        val obj = gson.fromJson<T>(text, type)
        callback.onDataReceived(obj)
    }
}