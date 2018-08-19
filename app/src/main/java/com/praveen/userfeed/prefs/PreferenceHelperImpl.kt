package com.praveen.userfeed.prefs

import android.content.Context
import android.content.SharedPreferences
import javax.inject.Inject

class PreferenceHelperImpl @Inject constructor(context: Context) : PreferenceHelper {

    companion object {
        const val APPLICATION_PREFERENCES_KEY = "user_feed_prefs"
        const val PREF_KEY_USER_AUTH_TOKEN = "PREF_KEY_USER_AUTH_TOKEN"
    }

    private val mPrefs: SharedPreferences

    init {
        mPrefs = context.getSharedPreferences(APPLICATION_PREFERENCES_KEY, Context.MODE_PRIVATE)
    }

    override fun setAuthorizationToken(token: String?) {
        mPrefs.edit().putString(PREF_KEY_USER_AUTH_TOKEN, token).apply()
    }

    override fun getAuthorizationToken(): String? {
        return mPrefs.getString(PREF_KEY_USER_AUTH_TOKEN, null)
    }
}