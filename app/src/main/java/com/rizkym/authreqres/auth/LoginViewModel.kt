package com.rizkym.authreqres.auth

import androidx.lifecycle.ViewModel
import com.rizkym.authreqres.remote.RepositoryAuth

class LoginViewModel(
    private val userRepository: RepositoryAuth,
) : ViewModel() {

    fun loginPost(email: String, password: String) = userRepository.loginPost(email, password)
}