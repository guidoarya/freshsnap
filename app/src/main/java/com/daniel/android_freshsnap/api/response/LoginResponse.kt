package com.daniel.android_freshsnap.api.response

import com.google.gson.annotations.SerializedName

data class LoginResponse(

	@field:SerializedName("token")
	val token: String,

	@field:SerializedName("msg")
	val msg: String,

	@field:SerializedName("error")
	val error: Boolean
)