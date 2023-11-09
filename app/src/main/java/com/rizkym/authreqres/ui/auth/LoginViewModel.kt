package com.rizkym.authreqres.ui.auth

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rizkym.authreqres.remote.AuthRepository
import kotlinx.coroutines.launch

class LoginViewModel(
    private val userRepository: AuthRepository,
) : ViewModel() {

    fun loginPost(email: String, password: String) = userRepository.loginPost(email, password)

    fun saveUser(token: String) {
        viewModelScope.launch {
            userRepository.saveUser(token)
        }
    }

}