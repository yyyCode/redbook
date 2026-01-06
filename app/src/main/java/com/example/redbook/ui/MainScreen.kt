package com.example.redbook.ui

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.redbook.ui.navigation.Screen
import com.example.redbook.ui.navigation.bottomNavItems
import com.example.redbook.ui.screens.HomeScreen
import com.example.redbook.ui.screens.MeScreen
import com.example.redbook.ui.screens.MessageScreen
import com.example.redbook.ui.screens.ShoppingScreen
import com.example.redbook.ui.screens.PublishScreen
import com.example.redbook.ui.screens.PostDetailScreen
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.result.PickVisualMediaRequest
import androidx.navigation.NavType
import androidx.navigation.navArgument
import androidx.navigation.navDeepLink

import androidx.compose.runtime.DisposableEffect
import androidx.compose.ui.platform.LocalContext
import android.content.Intent
import com.example.redbook.service.WebSocketService
import android.os.Build

import androidx.core.util.Consumer
import androidx.activity.ComponentActivity
import android.net.Uri
import androidx.compose.runtime.remember
import androidx.compose.runtime.mutableStateOf

@Composable
fun MainScreen() {
    val context = LocalContext.current
    
    // 启动 WebSocketService
    DisposableEffect(Unit) {
        val intent = Intent(context, WebSocketService::class.java)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            context.startService(intent)
        } else {
            context.startService(intent)
        }
        onDispose {}
    }

    val navController = rememberNavController()

    // 处理 DeepLink (热启动/onNewIntent)，避免重复处理
    val lastHandledUri = remember { mutableStateOf<Uri?>(null) }
    DisposableEffect(Unit) {
        val activity = context as? ComponentActivity
        val listener = Consumer<Intent> { intent ->
            val uri = intent.data
            if (uri != null && uri != lastHandledUri.value) {
                navController.handleDeepLink(intent)
                lastHandledUri.value = uri
            }
        }
        activity?.addOnNewIntentListener(listener)
        onDispose { activity?.removeOnNewIntentListener(listener) }
    }

    val photoPickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickVisualMedia(),
        onResult = { uri ->
            if (uri != null) {
                navController.navigate(Screen.Publish.createRoute(uri.toString()))
            }
        }
    )

    Scaffold(
        bottomBar = {
            val navBackStackEntry by navController.currentBackStackEntryAsState()
            val currentDestination = navBackStackEntry?.destination
            // Hide bottom bar on Publish screen
            if (currentDestination?.route?.startsWith("publish/") != true) {
                NavigationBar(
                    containerColor = Color.White
                ) {
                    bottomNavItems.forEach { screen ->
                        NavigationBarItem(
                            icon = { screen.icon?.let { Icon(it, contentDescription = screen.title) } },
                            label = { Text(screen.title) },
                            selected = currentDestination?.hierarchy?.any { it.route == screen.route } == true,
                            onClick = {
                                if (screen.route == Screen.Add.route) {
                                    photoPickerLauncher.launch(
                                        PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly)
                                    )
                                } else {
                                    navController.navigate(screen.route) {
                                        popUpTo(navController.graph.findStartDestination().id)
                                        launchSingleTop = true
                                    }
                                }
                            },
                            colors = NavigationBarItemDefaults.colors(
                                selectedIconColor = Color.Red,
                                selectedTextColor = Color.Red,
                                indicatorColor = Color.Transparent
                            )
                        )
                    }
                }
            }
        }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = Screen.Home.route,
            modifier = Modifier.padding(innerPadding)
        ) {
            composable(Screen.Home.route) { HomeScreen() }
            composable(Screen.Shopping.route) { ShoppingScreen() }
            composable(
                route = Screen.Message.route,
                deepLinks = listOf(
                    navDeepLink { uriPattern = "redbook://message" }
                )
            ) { MessageScreen() }
            composable(Screen.Me.route) { 
                MeScreen(
                    onPublishClick = {
                        photoPickerLauncher.launch(
                            PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly)
                        )
                    }
                ) 
            }
            
            composable(
                route = Screen.PostDetail.route,
                arguments = listOf(navArgument("postId") { type = NavType.StringType }),
                deepLinks = listOf(
                    navDeepLink { uriPattern = "redbook://post/{postId}" }
                )
            ) { backStackEntry ->
                val postId = backStackEntry.arguments?.getString("postId") ?: ""
                PostDetailScreen(
                    postId = postId,
                    onBackClick = { navController.popBackStack() }
                )
            }

            composable(
                route = Screen.Publish.route,
                arguments = listOf(navArgument("imageUri") { type = NavType.StringType })
            ) { backStackEntry ->
                val imageUri = backStackEntry.arguments?.getString("imageUri") ?: ""
                PublishScreen(
                    imageUri = imageUri,
                    onBackClick = { navController.popBackStack() },
                    onPublishClick = {
                        // TODO: Implement actual publish logic (save to DB, etc.)
                        navController.popBackStack()
                    }
                )
            }
        }
    }
}
