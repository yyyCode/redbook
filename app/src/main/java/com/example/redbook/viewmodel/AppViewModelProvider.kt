package com.example.redbook.viewmodel

import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.redbook.RedBookApplication

object AppViewModelProvider {
    val Factory = viewModelFactory {
        initializer {
            HomeViewModel(redBookApplication().container.postRepository)
        }
        initializer {
            ShoppingViewModel(redBookApplication().container.shoppingRepository)
        }
        initializer {
            MessageViewModel(
                redBookApplication().container.messageRepository,
                redBookApplication().container.notificationHelper
            )
        }
        initializer {
            MeViewModel(redBookApplication().container.userPreferencesRepository)
        }
        initializer {
            MainViewModel(redBookApplication().container.userPreferencesRepository)
        }
    }
}

fun CreationExtras.redBookApplication(): RedBookApplication =
    (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as RedBookApplication)
