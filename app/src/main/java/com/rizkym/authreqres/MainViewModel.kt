package com.rizkym.authreqres

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.rizkym.authreqres.utils.UserPreferences
import kotlinx.coroutines.launch

class MainViewModel(private val preferences: UserPreferences): ViewModel()  {

    fun logout() = deleteUserKey()

    private fun deleteUserKey(){
        viewModelScope.launch {
            preferences.deleteUserKey()
        }
    }

    fun getUserKey() = preferences.getUserKey().asLiveData()
}