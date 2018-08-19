package com.praveen.userfeed.api

import com.praveen.userfeed.data.PhotoDetails
import com.praveen.userfeed.prefs.PreferenceHelper
import com.praveen.userfeed.utils.ConversionUtils
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class ApiHelperImpl @Inject constructor(private val mUserFeedService: UserFeedService,
                                        private val mPreferenceHelper: PreferenceHelper): ApiHelper {

    override fun fetchUserFeed(): Observable<List<PhotoDetails>> {

        mPreferenceHelper.getAuthorizationToken()?:return Observable.empty()

        return mUserFeedService.requestUserPhotos(mPreferenceHelper.getAuthorizationToken().toString())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .map{feedResponse -> feedResponse.data}
                .flatMap { list -> Observable.fromIterable(list) }
                .map { feedItem -> ConversionUtils.getPhotoDetails(feedItem) }
                .toList().toObservable()
    }

    override fun postUserLikesPhoto(mediaId: String): Completable {
            return mUserFeedService.postUserLikesPhoto(mediaId, mPreferenceHelper.getAuthorizationToken().toString())
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
    }

    override fun postUserUnlikesPhoto(mediaId: String): Completable {
        return mUserFeedService.postUserUnlikesPhoto(mediaId,mPreferenceHelper.getAuthorizationToken().toString())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
    }
}