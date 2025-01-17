package com.rizkym.authreqres.di

import android.content.Context
import com.rizkym.authreqres.data.UserRepository
import com.rizkym.authreqres.database.UserDatabase
import com.rizkym.authreqres.network.config.ApiConfig

object Injection {
    fun provideRepository(context: Context): UserRepository {
        val database = UserDatabase.getDatabase(context)
        val apiService = ApiConfig.getApiService()
        return UserRepository(database, apiService)
    }
}