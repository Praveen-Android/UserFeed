package com.praveen.userfeed.api

import com.praveen.userfeed.data.PhotoDetails
import io.reactivex.Completable
import io.reactivex.Observable

interface ApiHelper {

    fun fetchUserFeed():Observable<List<PhotoDetails>>

    fun postUserLikesPhoto(mediaId: String):Completable

    fun postUserUnlikesPhoto(mediaId: String):Completable

}