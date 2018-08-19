package com.praveen.userfeed.ui

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import com.praveen.userfeed.repo.UserFeedRepository
import com.praveen.userfeed.ui.userfeed.UserFeedViewModel
import javax.inject.Inject

class UserFeedViewModelFactory @Inject constructor(private val userFeedRepository: UserFeedRepository) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(UserFeedViewModel::class.java)) {
            return UserFeedViewModel(userFeedRepository) as T
        } else {
            throw IllegalArgumentException("Unknown ViewModel class")
        }
    }
}