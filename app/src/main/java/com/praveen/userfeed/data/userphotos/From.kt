package com.praveen.userfeed.data.userphotos

import com.google.gson.annotations.SerializedName

data class From(

	@field:SerializedName("full_name")
	val fullName: String? = null,

	@field:SerializedName("profile_picture")
	val profilePicture: String? = null,

	@field:SerializedName("id")
	val id: String? = null,

	@field:SerializedName("username")
	val username: String? = null
)