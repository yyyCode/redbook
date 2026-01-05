package com.example.redbook.viewmodel

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

data class SuggestedUser(
    val id: Int,
    val name: String,
    val avatarUrl: String,
    val subtitle: String,
    val tag: String? = null,
    val followed: Boolean = false
)

class MessageViewModel : ViewModel() {
    private val _suggestedUsers = MutableStateFlow<List<SuggestedUser>>(emptyList())
    val suggestedUsers: StateFlow<List<SuggestedUser>> = _suggestedUsers.asStateFlow()

    init {
        loadSuggestedUsers()
    }

    private fun loadSuggestedUsers() {
        // Simulate loading data
        _suggestedUsers.value = listOf(
            SuggestedUser(1, "宠物小伙伴", "https://picsum.photos/80/80?random=11", "你的专属聊天搭子", "要找你", false),
            SuggestedUser(2, "睡眠好（三里陪拍）", "https://picsum.photos/80/80?random=12", "你近期赞过", null, false),
            SuggestedUser(3, "小陈超爱零条", "https://picsum.photos/80/80?random=13", "你近期赞过", null, false),
            SuggestedUser(4, "ice拿铁", "https://picsum.photos/80/80?random=14", "你近期赞过", null, false)
        )
    }
}
