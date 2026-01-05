package com.example.redbook.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "suggested_users")
data class SuggestedUserEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val name: String,
    val avatarUrl: String,
    val subtitle: String,
    val tag: String? = null,
    val followed: Boolean = false
)
