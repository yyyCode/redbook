package com.example.redbook.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.redbook.data.repository.UserPreferencesRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

data class UserProfile(
    val nickname: String,
    val redbookId: String,
    val avatarUrl: String,
    val followingCount: Int,
    val followersCount: Int,
    val likesAndCollectionsCount: Int
)

class MeViewModel(private val userPreferencesRepository: UserPreferencesRepository) : ViewModel() {
    val isDarkMode: StateFlow<Boolean> = userPreferencesRepository.isDarkMode
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = false
        )

    fun toggleDarkMode(enabled: Boolean) {
        viewModelScope.launch {
            userPreferencesRepository.setDarkMode(enabled)
        }
    }

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
