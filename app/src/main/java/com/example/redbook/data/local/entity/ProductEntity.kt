package com.example.redbook.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "products")
data class ProductEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val title: String,
    val imageUrl: String,
    val price: Double,
    val originalPrice: Double?,
    val salesCount: Int,
    val tags: List<String> = emptyList()
)
