package com.praveen.userfeed.di

import android.content.Context
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.praveen.userfeed.UserFeedApplication
import com.praveen.userfeed.api.ApiHelper
import com.praveen.userfeed.api.ApiHelperImpl
import com.praveen.userfeed.prefs.PreferenceHelper
import com.praveen.userfeed.prefs.PreferenceHelperImpl
import com.praveen.userfeed.repo.UserFeedRepository
import com.praveen.userfeed.repo.UserFeedRepositoryImpl
import com.praveen.userfeed.ui.UserFeedViewModelFactory
import dagger.Module
import dagger.Provides
import javax.inject.Singleton



@Module
class AppModule(private val userFeedApplication: UserFeedApplication) {

    @Provides
    @Singleton
    fun provideContext(): Context = userFeedApplication

    @Provides
    @Singleton
    fun provideGson(): Gson = GsonBuilder().create()

    @Provides
    @Singleton
    fun providePreferencesHelper(preferenceHelperImpl: PreferenceHelperImpl): PreferenceHelper {
        return preferenceHelperImpl
    }

    @Provides
    @Singleton
    fun provideApiHelper(apiHelperImpl: ApiHelperImpl): ApiHelper {
        return apiHelperImpl
    }

    @Provides
    @Singleton
    fun provideUserFeedRepository(userFeedRepositoryImpl: UserFeedRepositoryImpl): UserFeedRepository {
        return userFeedRepositoryImpl
    }

    @Provides
    @Singleton
    fun provideViewModelFactory(userFeedRepository: UserFeedRepository): UserFeedViewModelFactory {
        return UserFeedViewModelFactory(userFeedRepository)
    }

}