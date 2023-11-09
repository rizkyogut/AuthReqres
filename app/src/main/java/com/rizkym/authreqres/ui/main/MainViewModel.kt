package com.rizkym.authreqres.ui.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rizkym.authreqres.remote.AuthRepository
import kotlinx.coroutines.launch

class MainViewModel(private val repository: AuthRepository): ViewModel()  {

    fun logout() = deleteUserKey()

    private fun deleteUserKey(){
        viewModelScope.launch {
            repository.deleteUserKey()
        }
    }

    fun getUserKey() = repository.getUserKey()
}