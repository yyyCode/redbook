package com.example.redbook.service

import android.app.Service
import android.content.Intent
import android.os.IBinder
import com.example.redbook.RedBookApplication
import com.example.redbook.data.repository.MessageRepository
import com.example.redbook.util.NotificationHelper
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch
import org.json.JSONObject
import org.json.JSONException

class WebSocketService : Service() {

    private val serviceScope = CoroutineScope(SupervisorJob() + Dispatchers.IO)
    private lateinit var messageRepository: MessageRepository
    private lateinit var notificationHelper: NotificationHelper

    override fun onCreate() {
        super.onCreate()
        val appContainer = (application as RedBookApplication).container
        messageRepository = appContainer.messageRepository
        notificationHelper = appContainer.notificationHelper

        // 连接 WebSocket
        messageRepository.connectToWebSocket("ws://10.0.2.2:8080")

        // 监听消息并发送通知
        serviceScope.launch {
            messageRepository.messageFlow.collect { message ->
                try {
                    val obj = JSONObject(message)
                    val type = obj.optString("type", "")
                    if (type == "like") {
                        val postId = obj.optString("postId", "")
                        val text = obj.optString("text", "有人点赞了你")
                        if (postId.isNotEmpty()) {
                            notificationHelper.showPostNotification(
                                title = "点赞通知",
                                content = text,
                                postId = postId
                            )
                        } else {
                            notificationHelper.showNotification(title = "RedBook 新消息", content = text)
                        }
                    } else {
                        val text = obj.optString("text", message)
                        notificationHelper.showNotification(title = "RedBook 新消息", content = text)
                    }
                } catch (e: JSONException) {
                    notificationHelper.showNotification(
                        title = "RedBook 新消息",
                        content = message
                    )
                }
            }
        }
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        // START_STICKY 确保 Service 被杀后重启
        return START_STICKY
    }

    override fun onDestroy() {
        super.onDestroy()
        messageRepository.disconnectWebSocket()
        serviceScope.cancel()
    }

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }
}
