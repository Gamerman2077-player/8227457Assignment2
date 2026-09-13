package com.example.s8227457assignment2.ui.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.s8227457assignment2.data.repository.InvestmentRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val repository: InvestmentRepository
) : ViewModel() {

    private val _loginState =
        MutableStateFlow<LoginState>(LoginState.Idle)

    val loginState: StateFlow<LoginState> =
        _loginState.asStateFlow()

    fun login(
        username: String,
        password: String
    ) {
        if (username.isBlank() || password.isBlank()) {
            _loginState.value =
                LoginState.Error("Username and password are required.")
            return
        }

        viewModelScope.launch {
            _loginState.value = LoginState.Loading

            try {
                val response = repository.login(
                    username = username,
                    password = password
                )

                if (response.isSuccessful) {
                    val loginResponse = response.body()

                    if (loginResponse != null) {
                        _loginState.value =
                            LoginState.Success(loginResponse.keypass)
                    } else {
                        _loginState.value =
                            LoginState.Error("Empty response from server.")
                    }
                } else {
                    _loginState.value =
                        LoginState.Error(
                            "Login failed. Please check your username and password."
                        )
                }

            } catch (e: Exception) {
                _loginState.value =
                    LoginState.Error(
                        e.message ?: "Unable to connect to the server."
                    )
            }
        }
    }
}

sealed class LoginState {

    data object Idle : LoginState()

    data object Loading : LoginState()

    data class Success(
        val keypass: String
    ) : LoginState()

    data class Error(
        val message: String
    ) : LoginState()
}