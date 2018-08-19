package com.praveen.userfeed.api

import com.praveen.userfeed.data.userphotos.UserPhotoResponse
import io.reactivex.Completable
import io.reactivex.Observable
import retrofit2.http.*

interface  UserFeedService {

    /**
     * Get a list of logged in user Photos
     */
    @GET("/Prod/users/self/media/recent")
    @Headers("Content-type: application/json")
    fun requestUserPhotos(
            @Header("Authorization") authorization: String
    ): Observable<UserPhotoResponse>


    /**
     * Post when User likes a photo
     */
    @POST("/Prod/media/{media_id}/likes")
    @Headers("Content-type: application/json")
    fun postUserLikesPhoto(
            @Path("media_id") id: String, @Header("Authorization") authorization: String): Completable


    /**
     * Delete when User dislikes a photo
     */
    @DELETE("/Prod/media/{media_id}/likes")
    @Headers("Content-type: application/json")
    fun postUserUnlikesPhoto(
            @Path("media_id") id: String, @Header("Authorization") authorization: String): Completable
}