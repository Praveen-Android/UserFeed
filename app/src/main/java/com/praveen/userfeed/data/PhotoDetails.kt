package com.praveen.userfeed.data

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class PhotoDetails (

        var likesCount: Int,

        var mediaId: String,

        var userProfilePicUrl: String,

        var createdTime: String,

        var relativeTime: String,

        var standardImageUrl: String,

        var standardImageWidth: Int,

        var standardImageHeight: Int,

        var thumbnailImageUrl:String,

        var thumbnailImageWidth: Int,

        var thumbnailImageHeight: Int,

        var userHasLiked: Boolean

): Parcelable