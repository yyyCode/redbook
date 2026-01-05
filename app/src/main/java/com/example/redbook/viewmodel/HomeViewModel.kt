package com.example.redbook.viewmodel

import androidx.lifecycle.ViewModel
import com.example.redbook.model.Post
import com.example.redbook.model.mockPosts
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class HomeViewModel : ViewModel() {
    private val _followPosts = MutableStateFlow<List<Post>>(emptyList())
    val followPosts: StateFlow<List<Post>> = _followPosts.asStateFlow()

    private val _explorePosts = MutableStateFlow<List<Post>>(emptyList())
    val explorePosts: StateFlow<List<Post>> = _explorePosts.asStateFlow()

    private val _nearbyPosts = MutableStateFlow<List<Post>>(emptyList())
    val nearbyPosts: StateFlow<List<Post>> = _nearbyPosts.asStateFlow()

    init {
        // Simulate loading data
        loadPosts()
    }

    private fun loadPosts() {
        // In a real app, this would fetch from a repository/API
        _followPosts.value = mockPosts + mockPosts + mockPosts
        _explorePosts.value = mockPosts + mockPosts + mockPosts
        _nearbyPosts.value = mockPosts + mockPosts + mockPosts
    }
}
