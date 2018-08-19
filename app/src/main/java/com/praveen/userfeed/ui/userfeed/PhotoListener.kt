package com.praveen.userfeed.ui.userfeed

import com.praveen.userfeed.data.PhotoDetails
import com.praveen.userfeed.ui.data.PhotoStatus

interface PhotoListener {
    fun updatePhotoFavoriteStatus(mediaId: String, photoStatus: PhotoStatus, position:Int)
    fun onPhotoClicked(photoDetails: PhotoDetails)
}