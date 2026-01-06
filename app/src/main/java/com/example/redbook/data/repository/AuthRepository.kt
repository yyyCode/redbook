package com.example.redbook.data.repository

import com.example.redbook.data.model.LoginRequest
import com.example.redbook.data.model.UserInfo
import com.example.redbook.network.ApiService
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class AuthRepository(
    private val apiService: ApiService,
    private val userPreferencesRepository: UserPreferencesRepository
) {
    val isLoggedIn: Flow<Boolean> = userPreferencesRepository.authToken
        .map { !it.isNullOrEmpty() }

    suspend fun login(phone: String, code: String): Result<UserInfo> {
        return try {
            val response = apiService.login(LoginRequest(phone, code))
            if (response.success && response.token != null && response.user != null) {
                userPreferencesRepository.saveAuthToken(response.token, response.user.id)
                Result.success(response.user)
            } else {
                Result.failure(Exception(response.message ?: "Unknown error"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun logout() {
        userPreferencesRepository.clearAuth()
    }
}
