package com.example.redbook.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "posts")
data class PostEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val title: String,
    val imageUrl: String,
    val authorName: String,
    val authorAvatarUrl: String,
    val likeCount: Int,
    val isLiked: Boolean = false
)
