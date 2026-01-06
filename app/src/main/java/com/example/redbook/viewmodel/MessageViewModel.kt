package com.example.redbook.viewmodel

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

import androidx.lifecycle.viewModelScope
import com.example.redbook.data.repository.MessageRepository
import com.example.redbook.util.NotificationHelper
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

class MessageViewModel(
    private val messageRepository: MessageRepository,
    private val notificationHelper: NotificationHelper // 注入 NotificationHelper
) : ViewModel() {
    val suggestedUsers: StateFlow<List<SuggestedUser>> = messageRepository.suggestedUsers
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )

    private val _notifications = MutableStateFlow<List<String>>(emptyList())
    val notifications: StateFlow<List<String>> = _notifications.asStateFlow()

    init {
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
