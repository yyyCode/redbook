package com.example.redbook.data.remote

import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import okhttp3.WebSocket
import okhttp3.WebSocketListener
import okio.ByteString

class WebSocketManager {
    private val client = OkHttpClient()
    private var webSocket: WebSocket? = null

    // 用于发送接收到的消息
    private val _messageFlow = MutableSharedFlow<String>(
        replay = 0,
        extraBufferCapacity = 64,
        onBufferOverflow = BufferOverflow.DROP_OLDEST
    )
    val messageFlow: SharedFlow<String> = _messageFlow.asSharedFlow()

    // 连接状态
    private val _statusFlow = MutableSharedFlow<Boolean>(replay = 1)
    val statusFlow: SharedFlow<Boolean> = _statusFlow.asSharedFlow()

    fun connect(url: String) {
        val request = Request.Builder().url(url).build()
        webSocket = client.newWebSocket(request, object : WebSocketListener() {
            override fun onOpen(webSocket: WebSocket, response: Response) {
                _statusFlow.tryEmit(true)
                println("WebSocket Connected")
            }

            override fun onMessage(webSocket: WebSocket, text: String) {
                _messageFlow.tryEmit(text)
            }

            override fun onMessage(webSocket: WebSocket, bytes: ByteString) {
                _messageFlow.tryEmit(bytes.utf8())
            }

            override fun onClosing(webSocket: WebSocket, code: Int, reason: String) {
                webSocket.close(1000, null)
                _statusFlow.tryEmit(false)
                println("WebSocket Closing: $reason")
            }

            override fun onClosed(webSocket: WebSocket, code: Int, reason: String) {
                _statusFlow.tryEmit(false)
                println("WebSocket Closed")
            }

            override fun onFailure(webSocket: WebSocket, t: Throwable, response: Response?) {
                _statusFlow.tryEmit(false)
                println("WebSocket Failure: ${t.message}")
            }
        })
    }

    fun sendMessage(text: String) {
        webSocket?.send(text)
    }

    fun disconnect() {
        webSocket?.close(1000, "User disconnected")
        webSocket = null
    }
}
