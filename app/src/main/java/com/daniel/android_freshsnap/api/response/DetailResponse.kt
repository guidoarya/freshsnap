package com.daniel.android_freshsnap.api.response

import com.google.gson.annotations.SerializedName

data class DetailResponse(

	@field:SerializedName("Reference")
	val reference: List<ReferenceItem>,

	@field:SerializedName("Detail")
	val detail: List<DetailItem>
)

data class DetailItem(

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

data class ReferenceItem(

	@field:SerializedName("image")
	val image: String,

	@field:SerializedName("createdAt")
	val createdAt: String,

	@field:SerializedName("reference_name")
	val referenceName: String,

	@field:SerializedName("name")
	val name: String,

	@field:SerializedName("id")
	val id: Int,

	@field:SerializedName("updatedAt")
	val updatedAt: String
)
