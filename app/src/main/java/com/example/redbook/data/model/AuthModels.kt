package com.example.redbook.data.model

import com.google.gson.annotations.SerializedName

data class LoginRequest(
    val phone: String,
    val code: String
)

data class LoginResponse(
    val success: Boolean,
    val token: String?,
    val message: String?,
    val user: UserInfo?
)

data class UserInfo(
    val id: Int,
    val name: String,
    val avatar: String
)
