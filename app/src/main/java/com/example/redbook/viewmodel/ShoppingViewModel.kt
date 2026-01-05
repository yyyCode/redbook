package com.example.redbook.viewmodel

import androidx.lifecycle.ViewModel
import com.example.redbook.model.Product
import com.example.redbook.model.mockProducts
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

import androidx.lifecycle.viewModelScope
import com.example.redbook.data.repository.ShoppingRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn

class ShoppingViewModel(shoppingRepository: ShoppingRepository) : ViewModel() {
    val products: StateFlow<List<Product>> = shoppingRepository.products
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )

    private val _searchQuery = MutableStateFlow("")
    val searchQuery: StateFlow<String> = _searchQuery.asStateFlow()

    fun onSearchQueryChange(query: String) {
        _searchQuery.value = query
        // Implement search logic here if needed
    }
}
