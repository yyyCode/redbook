package com.example.redbook.viewmodel

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

import androidx.lifecycle.viewModelScope
import com.example.redbook.data.repository.MessageRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn


data class SuggestedUser(
    val id: Int,
    val name: String,
    val avatarUrl: String,
    val subtitle: String,
    val tag: String? = null,
    val followed: Boolean = false
)

class MessageViewModel(messageRepository: MessageRepository) : ViewModel() {
    val suggestedUsers: StateFlow<List<SuggestedUser>> = messageRepository.suggestedUsers
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )
}
