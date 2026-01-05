package com.example.redbook.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.redbook.data.local.entity.SuggestedUserEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface SuggestedUserDao {
    @Query("SELECT * FROM suggested_users")
    fun getAllSuggestedUsers(): Flow<List<SuggestedUserEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSuggestedUsers(users: List<SuggestedUserEntity>)
}
