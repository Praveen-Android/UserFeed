package com.praveen.userfeed.utils

import com.praveen.userfeed.data.PhotoDetails


fun convertStreamToString(stream: java.io.InputStream): String {
    val s = java.util.Scanner(stream).useDelimiter("\\A")
    return if (s.hasNext()) s.next() else ""
}

fun getSamplePhotoDetails(): PhotoDetails{
    return PhotoDetails(createdTime = "1534614350",
            relativeTime = "21m ago",
            userHasLiked = true,
            standardImageUrl = "https://s3-us-west-2.amazonaws.com/images-23-pete/vp/0e71f86a388343397d07a1847622a555/5B46BAEE/t51.2885-15/s640x640/sh0.08/e35/14515809_1721026988223961_1127353186336636928_n.jpg",
            mediaId = "1379395956948247718_4087388754",
            likesCount = 12,
            standardImageWidth = 0,
            standardImageHeight = 0,
            thumbnailImageUrl = "",
            thumbnailImageWidth= 0,
            thumbnailImageHeight= 0,
            userProfilePicUrl = "")
}