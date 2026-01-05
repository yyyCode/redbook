package com.example.redbook.data.repository

import com.example.redbook.data.local.dao.ProductDao
import com.example.redbook.data.local.entity.ProductEntity
import com.example.redbook.model.Product
import com.example.redbook.model.mockProducts
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map

class ShoppingRepository(private val productDao: ProductDao) {
    val products: Flow<List<Product>> = productDao.getAllProducts().map { entities ->
        entities.map { entity ->
            Product(
                id = entity.id,
                title = entity.title,
                imageUrl = entity.imageUrl,
                price = entity.price,
                originalPrice = entity.originalPrice,
                salesCount = entity.salesCount,
                tags = entity.tags
            )
        }
    }

    suspend fun initializeData() {
        if (productDao.getAllProducts().first().isEmpty()) {
            val entities = mockProducts.map { product ->
                ProductEntity(
                    title = product.title,
                    imageUrl = product.imageUrl,
                    price = product.price,
                    originalPrice = product.originalPrice,
                    salesCount = product.salesCount,
                    tags = product.tags
                )
            }
            productDao.insertProducts(entities)
        }
    }
}
