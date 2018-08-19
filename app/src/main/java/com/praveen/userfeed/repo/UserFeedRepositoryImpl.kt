package com.praveen.userfeed.repo

import com.praveen.userfeed.api.ApiHelper
import com.praveen.userfeed.data.PhotoDetails
import com.praveen.userfeed.prefs.PreferenceHelper
import io.reactivex.Completable
import io.reactivex.Observable
import javax.inject.Inject

class UserFeedRepositoryImpl @Inject constructor(private val mApiHelper: ApiHelper,
                                                 private val mPreferenceHelper: PreferenceHelper): UserFeedRepository {

    override fun fetchUserFeed(): Observable<List<PhotoDetails>> {
        return mApiHelper.fetchUserFeed()
    }

    override fun postUserLikesPhoto(mediaId:String): Completable {
        return mApiHelper.postUserLikesPhoto(mediaId)
    }

    override fun postUserUnlikesPhoto(mediaId:String): Completable{
        return mApiHelper.postUserUnlikesPhoto(mediaId)
    }

    override fun setAuthorizationToken(token:String?) {
        mPreferenceHelper.setAuthorizationToken(token)
    }

    override fun getAuthorizationToken(): String? {
        return mPreferenceHelper.getAuthorizationToken()
    }
}