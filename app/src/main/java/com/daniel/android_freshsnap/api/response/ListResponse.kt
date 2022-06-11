package com.daniel.android_freshsnap.api.response

import com.google.gson.annotations.SerializedName

data class ListResponse(

	@field:SerializedName("ListResponse")
	val listResponse: List<ListResponseItem>
){

data class ListResponseItem(

	@field:SerializedName("createdAt")
	val createdAt: String,

	@field:SerializedName("image")
	val image: String,

	@field:SerializedName("user_name")
	val userName: String,

	@field:SerializedName("location")
	val location: String,

	@field:SerializedName("item_name")
	val itemName: String,

	@field:SerializedName("id")
	val id: Int
)}
