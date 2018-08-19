package com.praveen.userfeed.utils

import com.praveen.userfeed.data.PhotoDetails
import com.praveen.userfeed.data.userphotos.UserPhoto

object ConversionUtils {

    fun getPhotoDetails(userPhoto: UserPhoto) : PhotoDetails {
        val likesCount = userPhoto.likes?.count
        val mediaId = userPhoto.mediaId
        val userProfilePicUrl = userPhoto.user?.profilePicture
        val createdTime = userPhoto.createdTime
        val relativeTime = getRelativeTime(createdTime!!.toDouble())
        val standardImageUrl = userPhoto.images?.standardResolution?.url
        val standardImageWidth = userPhoto.images?.standardResolution?.width
        val standardImageHeight = userPhoto.images?.standardResolution?.height
        val thumbnailImageUrl = userPhoto.images?.thumbnail?.url
        val thumbnailImageWidth = userPhoto.images?.thumbnail?.width
        val thumbnailImageHeight = userPhoto.images?.thumbnail?.height
        val userHasLiked = userPhoto.userHasLiked

        return PhotoDetails(likesCount = likesCount?:0,
                mediaId = mediaId?:"",
                userProfilePicUrl = userProfilePicUrl?:"",
                createdTime = createdTime?:"",
                relativeTime = relativeTime,
                standardImageUrl = standardImageUrl?:"",
                standardImageWidth = standardImageWidth?:0,
                standardImageHeight = standardImageHeight?:0,
                thumbnailImageUrl = thumbnailImageUrl?:"",
                thumbnailImageWidth = thumbnailImageWidth?:0,
                thumbnailImageHeight = thumbnailImageHeight?:0,
                userHasLiked = userHasLiked?:false

                )
    }

    fun getRelativeTime(createdTime: Double, currentTime:Double = System.currentTimeMillis().toDouble()): String {
        val mSecPerMinute = 60 * 1000
        val mSecPerHour = 60 * mSecPerMinute
        val mSecPerDay = 24 * mSecPerHour
        val mSecPerWeek = 7 * mSecPerDay

        val elapsedTime = currentTime.minus(createdTime*1000)

        if (elapsedTime<mSecPerMinute) {
            return Math.round(elapsedTime/1000).toString().plus("s ago")
        } else if(elapsedTime<mSecPerHour){
            return Math.round(elapsedTime/mSecPerMinute).toString().plus("m ago")
        } else if(elapsedTime<mSecPerDay){
            return Math.round(elapsedTime/mSecPerHour).toString().plus("h ago")
        } else if(elapsedTime<mSecPerWeek){
            return Math.round(elapsedTime/mSecPerDay).toString().plus("d ago")
        }

        return Math.round(elapsedTime/mSecPerWeek).toString().plus("w ago")
    }
}