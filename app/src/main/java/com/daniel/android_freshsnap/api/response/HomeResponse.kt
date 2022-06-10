package com.daniel.android_freshsnap.api.response

import com.google.gson.annotations.SerializedName

data class HomeResponse(

	@field:SerializedName("Vegetables")
	val vegetables: List<VegetablesItem>,

	@field:SerializedName("Fruits")
	val fruits: List<FruitsItem>
) {

	data class VegetablesItem(

		@field:SerializedName("image")
		val image: String,

		@field:SerializedName("createdAt")
		val createdAt: String,

		@field:SerializedName("howtokeep")
		val howtokeep: String,

		@field:SerializedName("name")
		val name: String,

		@field:SerializedName("id")
		val id: Int,

		@field:SerializedName("type")
		val type: String,

		@field:SerializedName("updatedAt")
		val updatedAt: String
	)

	data class FruitsItem(

		@field:SerializedName("image")
		val image: String,

		@field:SerializedName("createdAt")
		val createdAt: String,

		@field:SerializedName("howtokeep")
		val howtokeep: String,

		@field:SerializedName("name")
		val name: String,

		@field:SerializedName("id")
		val id: Int,

		@field:SerializedName("type")
		val type: String,

		@field:SerializedName("updatedAt")
		val updatedAt: String
	)
}
