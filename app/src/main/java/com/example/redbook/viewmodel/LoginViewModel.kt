package com.example.redbook.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.redbook.data.repository.AuthRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

sealed class LoginUiState {
    object Idle : LoginUiState()
    object Loading : LoginUiState()
    object Success : LoginUiState()
    data class Error(val message: String) : LoginUiState()
}

class LoginViewModel(private val authRepository: AuthRepository) : ViewModel() {
    private val _uiState = MutableStateFlow<LoginUiState>(LoginUiState.Idle)
    val uiState: StateFlow<LoginUiState> = _uiState.asStateFlow()

    private val _phone = MutableStateFlow("")
    val phone: StateFlow<String> = _phone.asStateFlow()

    private val _code = MutableStateFlow("")
    val code: StateFlow<String> = _code.asStateFlow()

    fun onPhoneChange(input: String) {
        _phone.value = input
    }

    fun onCodeChange(input: String) {
        _code.value = input
    }

    fun login() {
        if (_phone.value.isBlank() || _code.value.isBlank()) return

        viewModelScope.launch {
            _uiState.value = LoginUiState.Loading
            val result = authRepository.login(_phone.value, _code.value)
            result.onSuccess {
                _uiState.value = LoginUiState.Success
            }.onFailure {
                _uiState.value = LoginUiState.Error(it.message ?: "登录失败")
            }
        }
    }
    
    fun resetState() {
        _uiState.value = LoginUiState.Idle
        _phone.value = ""
        _code.value = ""
    }
}
