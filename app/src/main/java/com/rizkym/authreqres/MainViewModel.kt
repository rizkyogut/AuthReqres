package com.rizkym.authreqres

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rizkym.authreqres.remote.RepositoryAuth
import kotlinx.coroutines.launch

class MainViewModel(private val repository: RepositoryAuth): ViewModel()  {

    fun logout() = deleteUserKey()

    private fun deleteUserKey(){
        viewModelScope.launch {
            repository.deleteUserKey()
        }
    }

    fun getUserKey() = repository.getUserKey()
}