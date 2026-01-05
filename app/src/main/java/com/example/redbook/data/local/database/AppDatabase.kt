package com.example.redbook.data.local.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.redbook.data.local.Converters
import com.example.redbook.data.local.dao.PostDao
import com.example.redbook.data.local.dao.ProductDao
import com.example.redbook.data.local.dao.SuggestedUserDao
import com.example.redbook.data.local.entity.PostEntity
import com.example.redbook.data.local.entity.ProductEntity
import com.example.redbook.data.local.entity.SuggestedUserEntity

@Database(
    entities = [PostEntity::class, ProductEntity::class, SuggestedUserEntity::class],
    version = 1,
    exportSchema = false
)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun postDao(): PostDao
    abstract fun productDao(): ProductDao
    abstract fun suggestedUserDao(): SuggestedUserDao

    companion object {
        @Volatile
        private var Instance: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            return Instance ?: synchronized(this) {
                Room.databaseBuilder(context, AppDatabase::class.java, "redbook_database")
                    .fallbackToDestructiveMigration()
                    .build()
                    .also { Instance = it }
            }
        }
    }
}
