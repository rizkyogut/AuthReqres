package com.rizkym.authreqres.ui.auth

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rizkym.authreqres.network.Result
import com.rizkym.authreqres.network.config.ApiConfig
import com.rizkym.authreqres.network.response.LoginRequest
import com.rizkym.authreqres.network.response.LoginResponse
import com.rizkym.authreqres.utils.UserPreferences
import kotlinx.coroutines.launch
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class LoginViewModel(private val preferences: UserPreferences) : ViewModel() {
    private val _login = MutableLiveData<Result<String>>()
    val login: LiveData<Result<String>> = _login

    fun loginPost(email: String, password: String) {
        _login.postValue(Result.Loading())
        val client = ApiConfig.getApiService().login(LoginRequest(email, password))
        client.enqueue(object : Callback<LoginResponse> {
            override fun onResponse(call: Call<LoginResponse>, response: Response<LoginResponse>) {
                if (response.isSuccessful) {
                    val responseBody = response.body()?.token
                    responseBody?.let { saveUser(it) }
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
    }

    private fun saveUser(token: String) {
        viewModelScope.launch {
            preferences.saveUser(token)
        }
    }
}