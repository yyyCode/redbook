package com.example.redbook.viewmodel

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

data class UserProfile(
    val nickname: String,
    val redbookId: String,
    val avatarUrl: String,
    val followingCount: Int,
    val followersCount: Int,
    val likesAndCollectionsCount: Int
)

class MeViewModel : ViewModel() {
    private val _userProfile = MutableStateFlow(
        UserProfile(
            nickname = "烧仙草不加冰",
            redbookId = "11574481733",
            avatarUrl = "https://picsum.photos/100/100?random=21",
            followingCount = 5,
            followersCount = 0,
            likesAndCollectionsCount = 0
        )
    )
    val userProfile: StateFlow<UserProfile> = _userProfile.asStateFlow()

    private val _selectedTab = MutableStateFlow(0)
    val selectedTab: StateFlow<Int> = _selectedTab.asStateFlow()

    fun onTabSelected(index: Int) {
        _selectedTab.value = index
    }
}
