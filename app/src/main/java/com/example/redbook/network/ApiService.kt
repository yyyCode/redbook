package com.example.redbook.network

import com.example.redbook.data.model.LoginRequest
import com.example.redbook.data.model.LoginResponse
import retrofit2.http.Body
import retrofit2.http.POST

interface ApiService {
    @POST("login")
    suspend fun login(@Body request: LoginRequest): LoginResponse
}
