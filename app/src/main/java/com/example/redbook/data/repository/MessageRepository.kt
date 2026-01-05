package com.example.redbook.data.repository

import com.example.redbook.data.local.dao.SuggestedUserDao
import com.example.redbook.data.local.entity.SuggestedUserEntity
import com.example.redbook.viewmodel.SuggestedUser
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map

class MessageRepository(private val suggestedUserDao: SuggestedUserDao) {
    val suggestedUsers: Flow<List<SuggestedUser>> = suggestedUserDao.getAllSuggestedUsers().map { entities ->
        entities.map { entity ->
            SuggestedUser(
                id = entity.id,
                name = entity.name,
                avatarUrl = entity.avatarUrl,
                subtitle = entity.subtitle,
                tag = entity.tag,
                followed = entity.followed
            )
        }
    }

    suspend fun initializeData() {
        if (suggestedUserDao.getAllSuggestedUsers().first().isEmpty()) {
            val mockUsers = listOf(
                SuggestedUser(1, "宠物小伙伴", "https://picsum.photos/80/80?random=11", "你的专属聊天搭子", "要找你", false),
                SuggestedUser(2, "睡眠好（三里陪拍）", "https://picsum.photos/80/80?random=12", "你近期赞过", null, false),
                SuggestedUser(3, "小陈超爱零条", "https://picsum.photos/80/80?random=13", "你近期赞过", null, false),
                SuggestedUser(4, "ice拿铁", "https://picsum.photos/80/80?random=14", "你近期赞过", null, false)
            )
            val entities = mockUsers.map { user ->
                SuggestedUserEntity(
                    name = user.name,
                    avatarUrl = user.avatarUrl,
                    subtitle = user.subtitle,
                    tag = user.tag,
                    followed = user.followed
                )
            }
            suggestedUserDao.insertSuggestedUsers(entities)
        }
    }
}
