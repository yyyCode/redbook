package com.example.redbook

import android.app.Application
import com.example.redbook.data.local.database.AppDatabase
import com.example.redbook.data.repository.MessageRepository
import com.example.redbook.data.repository.PostRepository
import com.example.redbook.data.repository.ShoppingRepository
import com.example.redbook.data.repository.UserPreferencesRepository
import com.example.redbook.data.repository.dataStore
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class RedBookApplication : Application() {
    lateinit var container: AppContainer

    override fun onCreate() {
        super.onCreate()
        container = AppContainer(this)
        
        // Initialize DB with mock data
        CoroutineScope(Dispatchers.IO).launch {
            container.postRepository.initializeData()
            container.shoppingRepository.initializeData()
            container.messageRepository.initializeData()
        }
    }
}

class AppContainer(private val context: Application) {
    val database by lazy { AppDatabase.getDatabase(context) }
    
    val postRepository by lazy { PostRepository(database.postDao()) }
    val shoppingRepository by lazy { ShoppingRepository(database.productDao()) }
    val messageRepository by lazy { MessageRepository(database.suggestedUserDao()) }
    val userPreferencesRepository by lazy { UserPreferencesRepository(context.dataStore) }
}
