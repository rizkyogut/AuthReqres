package com.rizkym.authreqres.utils

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.rizkym.authreqres.ui.MainViewModel
import com.rizkym.authreqres.ui.auth.LoginViewModel

//class ViewModelFactory(private val preferences: UserPreferences) :
//    ViewModelProvider.NewInstanceFactory() {
//    private lateinit var mApplication: Application
//
//    fun setApplication(application: Application) {
//        mApplication = application
//    }
//
//    @Suppress("UNCHECKED_CAST")
//    override fun <T : ViewModel> create(modelClass: Class<T>): T {
//        if (modelClass.isAssignableFrom(LoginViewModel::class.java)) {
//            return LoginViewModel(preferences) as T
//        }
//        if (modelClass.isAssignableFrom(MainViewModel::class.java)) {
//            return MainViewModel(preferences) as T
//        } else throw IllegalArgumentException("Unknown ViewModel class: " + modelClass.name)
//    }
//}