package com.rizkym.authreqres.utils

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.rizkym.authreqres.MainViewModel
import com.rizkym.authreqres.auth.LoginViewModel
import com.rizkym.authreqres.remote.Repository
import com.rizkym.authreqres.remote.RepositoryAuth

class ViewModelFactory(private val repository: RepositoryAuth) :
    ViewModelProvider.NewInstanceFactory() {

    private lateinit var mApplication: Application

    fun setApplication(application: Application) {
        mApplication = application
    }

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(LoginViewModel::class.java)) {
            return LoginViewModel(repository) as T
        }
        if (modelClass.isAssignableFrom(MainViewModel::class.java)) {
            return MainViewModel(repository) as T
        } else throw IllegalArgumentException("Unknown ViewModel class: " + modelClass.name)
    }
}