package com.praveen.userfeed.ui.userfeed

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import android.util.Log
import com.praveen.userfeed.data.PhotoDetails
import com.praveen.userfeed.repo.UserFeedRepository
import com.praveen.userfeed.ui.data.PhotoStatus
import com.praveen.userfeed.ui.data.Response
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject

/**
 * UserFeedViewModel class to delegate the requests to repository and send the response to the view using LiveData
 */
class UserFeedViewModel @Inject constructor(private val userFeedRepository: UserFeedRepository): ViewModel() {

    private val compositeDisposable = CompositeDisposable()

    //Live Date object and function for communication between view and viewmodel
    private val response: MutableLiveData<Response> = MutableLiveData()
    fun response(): MutableLiveData<Response> = response

    //function to fetch the user feed items
    fun fetchUserFeed() {
        compositeDisposable.add(userFeedRepository.fetchUserFeed()
                .doOnSubscribe { response.postValue(Response.loading()) }
                .subscribe(
                        { photoDetailsList: List<PhotoDetails>? -> run {
                            Log.d(javaClass.simpleName, "fetch user feed successful")
                            response.postValue(Response.success(photoDetailsList))
                            }
                        },
                        { throwable: Throwable? -> run {
                            Log.d(javaClass.simpleName, "fetch user feed failed")
                            response.postValue(Response.error(throwable))
                            }
                        }
                )
        )
    }

    // function to post user likes photo event
    fun postUserLikesPhoto(mediaId:String){
        compositeDisposable.add(userFeedRepository.postUserLikesPhoto(mediaId)
                .subscribe(
                        {
                            Log.d(javaClass.simpleName, "photo like successful")
                            response.postValue(Response.success(PhotoStatus.LIKE))
                        },
                        {
                            Log.d(javaClass.simpleName, "photo like failed")
                            response.postValue(Response.error(error = it, data = PhotoStatus.LIKE))
                        }
                )
        )
    }

    // function to post user unlikes photo event
    fun postUserUnlikesPhoto(mediaId:String){
        compositeDisposable.add(userFeedRepository.postUserUnlikesPhoto(mediaId)
                .subscribe(
                        {
                            Log.d(javaClass.simpleName, "photo unlike successful")
                            response.postValue(Response.success(PhotoStatus.UNLIKE))
                        },
                        {
                            Log.d(javaClass.simpleName, "photo unlike failed")
                            response.postValue(Response.error(error = it, data = PhotoStatus.UNLIKE))
                        }
                )
        )
    }

    fun getAuthorizationToken(): String? {
        return userFeedRepository.getAuthorizationToken()
    }

    fun setAuthorizationToken(token: String?) {
        userFeedRepository.setAuthorizationToken(token)
    }

    //clean up the subscriptions when view model is destroyed
    override fun onCleared() {
        compositeDisposable.clear()
        super.onCleared()
    }

}