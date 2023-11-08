package com.rizkym.authreqres.remote

import androidx.lifecycle.asLiveData
import com.rizkym.authreqres.remote.config.ApiConfig
import com.rizkym.authreqres.remote.response.LoginRequest
import com.rizkym.authreqres.utils.UserPreferences
import org.json.JSONObject

class Repository(private val preferences: UserPreferences) {

    suspend fun login(email: String, password: String): Result<String> {
        try {
            val client = ApiConfig.getApiService().login(LoginRequest(email, password))
            val response = client.execute()

            return if (response.isSuccessful) {
                val responseBody = response.body()?.token
                responseBody?.let { saveUser(it) }
                Result.Success(responseBody)
            } else {
                val errorBody = response.errorBody()?.string()
                val jsonObject = errorBody?.let {
                    JSONObject(it).getString("error")
                }
                Result.Error(jsonObject)
            }
        } catch (e: Exception) {
            return Result.Error(e.message ?: "An error occurred")
        }
    }

    private suspend fun saveUser(token: String) {
        preferences.saveUser(token)
    }

    suspend fun logout() = deleteUserKey()

    private suspend fun deleteUserKey() = preferences.deleteUserKey()

    fun getUserKey() = preferences.getUserKey().asLiveData()
}