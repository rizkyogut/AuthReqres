package com.rizkym.authreqres.remote.config

import com.rizkym.authreqres.remote.response.LoginRequest
import com.rizkym.authreqres.remote.response.LoginResponse
import com.rizkym.authreqres.remote.response.UserResponse
import retrofit2.Call
import retrofit2.http.*

interface ApiService {

    @POST("login")
    fun login(@Body request: LoginRequest): Call<LoginResponse>

    @GET("users")
    suspend fun getUsersPages(
        @Query("page") page: Int,
        @Query("per_page") perPage: Int
    ): UserResponse
}