package com.praveen.userfeed.data.userphotos

import com.google.gson.annotations.SerializedName

data class Thumbnail(

	@field:SerializedName("width")
	val width: Int? = null,

	@field:SerializedName("url")
	val url: String? = null,

	@field:SerializedName("height")
	val height: Int? = null
)