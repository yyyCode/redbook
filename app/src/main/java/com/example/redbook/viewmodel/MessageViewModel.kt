package com.example.redbook.viewmodel

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

import androidx.lifecycle.viewModelScope
import com.example.redbook.data.repository.MessageRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch


data class SuggestedUser(
    val id: Int,
    val name: String,
    val avatarUrl: String,
    val subtitle: String,
    val tag: String? = null,
    val followed: Boolean = false
)

class MessageViewModel(private val messageRepository: MessageRepository) : ViewModel() {
    val suggestedUsers: StateFlow<List<SuggestedUser>> = messageRepository.suggestedUsers
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )

    private val _notifications = MutableStateFlow<List<String>>(emptyList())
    val notifications: StateFlow<List<String>> = _notifications.asStateFlow()

    init {
        // 连接 WebSocket 服务
        // Android 模拟器使用 10.0.2.2 访问宿主机 localhost
        // 如果是真机调试，请使用电脑局域网 IP
        messageRepository.connectToWebSocket("ws://10.0.2.2:8080")

        viewModelScope.launch {
            messageRepository.messageFlow.collect { message ->
                _notifications.value = _notifications.value + message
            }
        }
    }
    
    fun clearAllNotifications() {
        _notifications.value = emptyList()
    }

    override fun onCleared() {
        super.onCleared()
        messageRepository.disconnectWebSocket()
    }
}
