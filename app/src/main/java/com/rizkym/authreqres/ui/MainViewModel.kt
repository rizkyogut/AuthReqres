package com.rizkym.authreqres.ui

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.rizkym.authreqres.data.UserRepository
import com.rizkym.authreqres.di.Injection
import com.rizkym.authreqres.network.response.DataItem
import com.rizkym.authreqres.ui.auth.LoginViewModel
import com.rizkym.authreqres.utils.UserPreferences
import kotlinx.coroutines.launch

class MainViewModel(private val preferences: UserPreferences, userRepository: UserRepository) :
    ViewModel() {

    fun logout() = deleteUserKey()

    private fun deleteUserKey() {
        viewModelScope.launch {
            preferences.deleteUserKey()
        }
    }

    fun getUserKey() = preferences.getUserKey().asLiveData()

    val quota: LiveData<PagingData<DataItem>> = userRepository.getUser().cachedIn(viewModelScope)
}

class ViewModelFactory(private val context: Context, private val preferences: UserPreferences) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(MainViewModel::class.java) -> {
                MainViewModel(preferences, Injection.provideRepository(context)) as T
            }
            modelClass.isAssignableFrom(LoginViewModel::class.java) -> {
                LoginViewModel(preferences) as T
            }
            else -> throw IllegalArgumentException("Unknown ViewModel class")
        }
    }
}