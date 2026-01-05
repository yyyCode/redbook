package com.example.redbook.ui.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.PlayCircle
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.AddBox
import androidx.compose.ui.graphics.vector.ImageVector

sealed class Screen(val route: String, val title: String, val icon: ImageVector? = null) {
    object Home : Screen("home", "首页", Icons.Default.Home)
    object Video : Screen("video", "视频", Icons.Default.PlayCircle)
    object Add : Screen("add", "发布", Icons.Default.AddBox)
    object Message : Screen("message", "消息", Icons.Default.Email)
    object Me : Screen("me", "我", Icons.Default.Person)
}

val bottomNavItems = listOf(
    Screen.Home,
    Screen.Video,
    Screen.Add,
    Screen.Message,
    Screen.Me
)
