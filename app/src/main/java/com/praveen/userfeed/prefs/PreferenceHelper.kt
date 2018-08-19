package com.praveen.userfeed.prefs

interface PreferenceHelper {
    fun setAuthorizationToken(token: String?)
    fun getAuthorizationToken(): String?
}