package com.rizkym.authreqres.network.config

import com.rizkym.authreqres.network.response.LoginRequest
import com.rizkym.authreqres.network.response.LoginResponse
import com.rizkym.authreqres.network.response.UserResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface ApiService {

    @POST("login")
    fun login(@Body request: LoginRequest): Call<LoginResponse>

    @GET("users")
    suspend fun getUsersPages(
        @Query("page") page: Int,
        @Query("per_page") perPage: Int
    ): UserResponse

//    @GET("users")
//    suspend fun getUsersPages(
//        @Query("page") page: Int,
//        @Query("per_page") perPage: Int
//    ): List<DataItem>
}