package com.example.redbook.data.repository

import com.example.redbook.data.local.dao.PostDao
import com.example.redbook.data.local.entity.PostEntity
import com.example.redbook.model.Post
import com.example.redbook.model.mockPosts
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map

class PostRepository(private val postDao: PostDao) {
    val posts: Flow<List<Post>> = postDao.getAllPosts().map { entities ->
        entities.map { entity ->
            Post(
                id = entity.id,
                title = entity.title,
                imageUrl = entity.imageUrl,
                authorName = entity.authorName,
                authorAvatarUrl = entity.authorAvatarUrl,
                likeCount = entity.likeCount,
                isLiked = entity.isLiked
            )
        }
    }

    suspend fun initializeData() {
        if (postDao.getAllPosts().first().isEmpty()) {
            val entities = mockPosts.map { post ->
                PostEntity(
                    // Let Room auto-generate ID if needed, but we can reuse mock IDs or ignore them
                    // Since mockPosts have IDs, we can use them or 0.
                    // If we use 0, Room generates new ones.
                    // Let's ignore mock IDs for simplicity and let DB handle it, or use them.
                    title = post.title,
                    imageUrl = post.imageUrl,
                    authorName = post.authorName,
                    authorAvatarUrl = post.authorAvatarUrl,
                    likeCount = post.likeCount,
                    isLiked = post.isLiked
                )
            }
            postDao.insertPosts(entities)
        }
    }
}
