package com.praveen.userfeed.di

import com.praveen.userfeed.ui.UserFeedActivity
import com.praveen.userfeed.ui.feeddetails.PhotoDetailFragment
import com.praveen.userfeed.ui.auth.AuthenticationActivity
import com.praveen.userfeed.ui.userfeed.UserFeedAdapter
import com.praveen.userfeed.ui.userfeed.UserFeedFragment
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component (modules = arrayOf(AppModule::class, ApiModule::class))
interface AppComponent {

    fun inject(mainActivity: UserFeedActivity)

    fun inject(authenticationActivity: AuthenticationActivity)

    fun inject(userFeedFragment: UserFeedFragment)

    fun inject(userFeedAdapter: UserFeedAdapter)

    fun inject(photoDetailFragment: PhotoDetailFragment)


}