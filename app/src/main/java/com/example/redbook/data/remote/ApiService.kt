package com.example.redbook.data.remote

import retrofit2.http.GET
import retrofit2.http.Query

// 定义后端 API 接口
interface ApiService {
    
    // 示例：获取首页推荐列表
    // 实际请求会是: GET /api/feed?type=recommend
    @GET("feed")
    suspend fun getHomeFeed(
        @Query("type") type: String = "recommend"
    ): List<FeedItem> // 这里暂时使用 FeedItem 占位，实际需要定义对应的数据类

    // 示例：获取用户信息
    @GET("user/profile")
    suspend fun getUserProfile(): UserProfileDto
}

// 简单的占位数据类，用于演示
data class FeedItem(
    val id: String,
    val title: String,
    val imageUrl: String
)

data class UserProfileDto(
    val id: String,
    val nickname: String,
    val avatar: String
)
