package com.praveen.userfeed.data.userphotos

import com.google.gson.annotations.SerializedName

data class Images(

        @field:SerializedName("thumbnail")
	val thumbnail: Thumbnail? = null,

        @field:SerializedName("low_resolution")
	val lowResolution: LowResolution? = null,

        @field:SerializedName("standard_resolution")
	val standardResolution: StandardResolution? = null
)