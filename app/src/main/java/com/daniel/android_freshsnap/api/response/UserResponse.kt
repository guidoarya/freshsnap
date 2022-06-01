package com.daniel.android_freshsnap.api.response

import com.google.gson.annotations.SerializedName

data class UserResponse(

	@field:SerializedName("UserResponse")
	val userResponse: List<UserResponseItem>
)

data class UserResponseItem(

	@field:SerializedName("image")
	val image: String,

	@field:SerializedName("name")
	val name: String,

	@field:SerializedName("id")
	val id: Int,

	@field:SerializedName("email")
	val email: String
)
