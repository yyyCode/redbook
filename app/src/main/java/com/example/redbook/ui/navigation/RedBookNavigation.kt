package com.example.redbook.ui.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.AddBox
import androidx.compose.ui.graphics.vector.ImageVector

import android.net.Uri

sealed class Screen(val route: String, val title: String, val icon: ImageVector? = null) {
    object Home : Screen("home", "首页", Icons.Default.Home)
    object Shopping : Screen("shopping", "购物", Icons.Default.ShoppingCart)
    object Add : Screen("add", "发布", Icons.Default.AddBox)
    object Message : Screen("message", "消息", Icons.Default.Email)
    object Me : Screen("me", "我", Icons.Default.Person)

    object Publish : Screen("publish/{imageUri}", "发布") {
        fun createRoute(imageUri: String) = "publish/${Uri.encode(imageUri)}"
    }
}

val bottomNavItems = listOf(
    Screen.Home,
    Screen.Shopping,
    Screen.Add,
    Screen.Message,
    Screen.Me
)
