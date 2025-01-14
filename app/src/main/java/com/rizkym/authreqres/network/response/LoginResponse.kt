package com.rizkym.authreqres.network.response

import com.google.gson.annotations.SerializedName

data class LoginResponse(

	@field:SerializedName("token")
	val token: String? = null,

	@field:SerializedName("error")
	val error: String? = null
)

data class LoginRequest(
	@SerializedName("email")
	val email: String,

	@SerializedName("password")
	val password: String,
)