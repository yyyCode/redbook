package com.example.redbook

import android.app.Application
import com.example.redbook.data.local.database.AppDatabase
import com.example.redbook.data.repository.MessageRepository
import com.example.redbook.data.repository.PostRepository
import com.example.redbook.data.repository.ShoppingRepository
import com.example.redbook.data.repository.UserPreferencesRepository
import com.example.redbook.data.repository.dataStore
import com.example.redbook.data.remote.WebSocketManager
import com.example.redbook.data.remote.ApiService
import com.example.redbook.util.NotificationHelper
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

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
    
    val notificationHelper by lazy { NotificationHelper(context) }

    // 1. 创建共享的 OkHttpClient
    // 这样 WebSocket 和 Retrofit 可以共享连接池、拦截器等配置
    private val okHttpClient by lazy {
        OkHttpClient.Builder()
            // 可以在这里添加通用的拦截器，比如 TokenInterceptor
            .build()
    }

    // 2. 配置 WebSocketManager 使用这个 Client
    val webSocketManager by lazy { WebSocketManager(okHttpClient) }

    // 3. 配置 Retrofit
    private val retrofit by lazy {
        Retrofit.Builder()
            .baseUrl("https://mock.api.com/") // 替换为真实的 Base URL
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    // 4. 创建 ApiService 实例
    val apiService: ApiService by lazy { retrofit.create(ApiService::class.java) }

    val postRepository by lazy { PostRepository(database.postDao()) }
    val shoppingRepository by lazy { ShoppingRepository(database.productDao()) }
    val messageRepository by lazy { MessageRepository(database.suggestedUserDao(), webSocketManager) }
    val userPreferencesRepository by lazy { UserPreferencesRepository(context.dataStore) }
}
