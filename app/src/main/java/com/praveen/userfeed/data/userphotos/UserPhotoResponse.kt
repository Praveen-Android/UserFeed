package com.praveen.userfeed.data.userphotos

import com.google.gson.annotations.SerializedName

data class UserPhotoResponse(

        @field:SerializedName("pagination")
	val pagination: Pagination? = null,

        @field:SerializedName("data")
	val data: List<UserPhoto?>? = null,

        @field:SerializedName("meta")
	val meta: Meta? = null
)