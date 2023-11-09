package com.rizkym.authreqres.remote

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.asLiveData
import com.rizkym.authreqres.remote.config.ApiConfig
import com.rizkym.authreqres.remote.response.LoginRequest
import com.rizkym.authreqres.remote.response.LoginResponse
import com.rizkym.authreqres.utils.UserPreferences
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AuthRepository(
    private val preferences: UserPreferences
) {
    private val _login = MutableLiveData<Result<String>>()
    val login: LiveData<Result<String>> = _login

    fun loginPost(email: String, password: String): LiveData<Result<String>> {
        val response = ApiConfig.getApiService().login(LoginRequest(email, password))
        response.enqueue(object : Callback<LoginResponse> {
            override fun onResponse(call: Call<LoginResponse>, response: Response<LoginResponse>) {
                if (response.isSuccessful) {
                    val responseBody = response.body()?.token
                    _login.postValue(Result.Success(responseBody))
                } else {
                    try {
                        response.errorBody()?.let {
                            val jsonObject = JSONObject(it.string()).getString("error")
                            _login.postValue(Result.Error(jsonObject))
                        }
                    } catch (e: Exception) {
                        _login.postValue(Result.Error("${e.message}"))
                    }
                }
            }

            override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                _login.postValue(Result.Error(t.message))
            }

        })

        return _login
    }

    suspend fun saveUser(token: String) = preferences.saveUser(token)

    fun getUserKey() = preferences.getUserKey().asLiveData()

    suspend fun deleteUserKey() = preferences.deleteUserKey()
}