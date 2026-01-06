package com.example.redbook

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.example.redbook.ui.MainScreen
import com.example.redbook.ui.theme.RedbookTheme

import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.redbook.viewmodel.AppViewModelProvider
import com.example.redbook.viewmodel.MainViewModel

import android.content.Intent

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val viewModel: MainViewModel = viewModel(factory = AppViewModelProvider.Factory)
            val isDarkMode by viewModel.isDarkMode.collectAsState()

            RedbookTheme(darkTheme = isDarkMode) {
                MainScreen()
            }
        }
    }

    override fun onNewIntent(intent: Intent) {
        super.onNewIntent(intent)
        setIntent(intent)
    }
}