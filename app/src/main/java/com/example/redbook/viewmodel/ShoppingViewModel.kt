package com.example.redbook.viewmodel

import androidx.lifecycle.ViewModel
import com.example.redbook.model.Product
import com.example.redbook.model.mockProducts
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class ShoppingViewModel : ViewModel() {
    private val _products = MutableStateFlow<List<Product>>(emptyList())
    val products: StateFlow<List<Product>> = _products.asStateFlow()

    private val _searchQuery = MutableStateFlow("")
    val searchQuery: StateFlow<String> = _searchQuery.asStateFlow()

    init {
        loadProducts()
    }

    private fun loadProducts() {
        _products.value = mockProducts + mockProducts
    }

    fun onSearchQueryChange(query: String) {
        _searchQuery.value = query
        // Implement search logic here if needed
    }
}
