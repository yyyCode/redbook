package com.example.redbook.viewmodel

import androidx.lifecycle.ViewModel
import com.example.redbook.model.Post
import com.example.redbook.model.mockPosts
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

import androidx.lifecycle.viewModelScope
import com.example.redbook.data.repository.PostRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn

class HomeViewModel(postRepository: PostRepository) : ViewModel() {
    val followPosts: StateFlow<List<Post>> = postRepository.posts
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )

    val explorePosts: StateFlow<List<Post>> = postRepository.posts
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )

    val nearbyPosts: StateFlow<List<Post>> = postRepository.posts
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )
}
