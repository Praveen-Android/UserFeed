package com.praveen.userfeed

import android.app.Application
import com.praveen.userfeed.di.ApiModule
import com.praveen.userfeed.di.AppComponent
import com.praveen.userfeed.di.AppModule
import com.praveen.userfeed.di.DaggerAppComponent

class UserFeedApplication: Application() {

    companion object {
        lateinit var appComponent: AppComponent
    }

    override fun onCreate() {
        super.onCreate()
        setUpDagger()
    }

    private fun setUpDagger() {
        appComponent = DaggerAppComponent.builder()
                .appModule(AppModule(this))
                .apiModule(ApiModule()).build()
    }
}