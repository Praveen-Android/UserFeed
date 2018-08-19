package com.praveen.userfeed.utils

import com.google.gson.Gson
import com.praveen.userfeed.data.userphotos.UserPhoto
import com.praveen.userfeed.data.userphotos.UserPhotoResponse
import com.praveen.userfeed.utils.ConversionUtils.getRelativeTime
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import java.util.*

@RunWith(JUnit4::class)
class ConversionUtilsTest {

    private lateinit var userPhotosList: List<UserPhoto?>

    @Before
    fun setUp(){
        val inputStream = this.javaClass.classLoader.getResourceAsStream("testjson_feed.json")
        val jsonSample = convertStreamToString(inputStream)
        val userPhotoResponse = Gson().fromJson(jsonSample, UserPhotoResponse::class.java)
        userPhotosList = userPhotoResponse.data!!
    }

    @After
    fun tearDown(){
        userPhotosList = emptyList()
    }

    @Test
     fun verifyGetPhotoDetails() {
        assertEquals(userPhotosList.size, 4)

        val photoDetails = ConversionUtils.getPhotoDetails(userPhotosList[0]!!)
        assertEquals(photoDetails.userHasLiked, false)
        assertEquals(photoDetails.createdTime, "1478656833")
        assertEquals(photoDetails.relativeTime, "93w ago")
        assertEquals(photoDetails.likesCount, 12)
        assertEquals(photoDetails.mediaId, "1379395956948247718_4087388754")
        assertEquals(photoDetails.userProfilePicUrl, "https://scontent.cdninstagram.com/vp/1a53935dc83b98f69ba8f1ca954cac27/5B41FEDD/t51.2885-19/s150x150/15034491_351635768511695_6329805359259058176_a.jpg")
        assertEquals(photoDetails.standardImageUrl, "https://s3-us-west-2.amazonaws.com/images-23-pete/vp/0e71f86a388343397d07a1847622a555/5B46BAEE/t51.2885-15/s640x640/sh0.08/e35/14515809_1721026988223961_1127353186336636928_n.jpg")
        assertEquals(photoDetails.thumbnailImageUrl, "https://s3-us-west-2.amazonaws.com/images-23-pete/vp/420401657df07877d22cdff2bcfa3a3e/5B3C7593/t51.2885-15/s150x150/e35/14515809_1721026988223961_1127353186336636928_n.jpg")
    }

    @Test
    fun verifyRelativeTime() {
        val date = Date(1534615620000)  // 08/18/2018 11:07:00
        val current = date.time.toDouble()
        assertEquals(getRelativeTime("1534615610".toDouble(), current), "10s ago") // 08/18/2018 11:06:50
        assertEquals(getRelativeTime("1534615550".toDouble(), current), "1m ago") // 08/18/2018 11:05:50
        assertEquals(getRelativeTime("1534614350".toDouble(), current), "21m ago") // 08/18/2018 10:45:50
        assertEquals(getRelativeTime("1534611950".toDouble(), current), "1h ago") // 08/18/2018 10:05:50
        assertEquals(getRelativeTime("1534608350".toDouble(), current), "2h ago") // 08/18/2018 9:05:50
        assertEquals(getRelativeTime("1534527950".toDouble(), current), "1d ago") // 08/17/2018 10:45:50
        assertEquals(getRelativeTime("1533923150".toDouble(), current), "1w ago") // 08/10/2018 10:45:50
        assertEquals(getRelativeTime("1502387150".toDouble(), current), "53w ago") // 08/10/2017 10:45:50
    }

    @Test
    fun verifyPluralizeUtil() {
        assertEquals("Like".pluralize(1), "Like")
        assertEquals("Like".pluralize(2), "Likes")
    }
}