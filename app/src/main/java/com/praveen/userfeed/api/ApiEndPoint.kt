package com.praveen.userfeed.api

import android.net.Uri

object ApiEndPoint {

    const val BASE_AUTH_URL = "https://insta23prod.auth.us-west-2.amazoncognito.com"
    const val BASE_CONTENT_URL = "https://kqlpe1bymk.execute-api.us-west-2.amazonaws.com"
    const val CLIENT_ID = "5khm2intordkd1jjr7rbborbfj"
    const val AUTH_RESPONSE_TYPE = "token"
    const val OAUTH_REDIRECT_URI = "https://www.23andme.com/"

    fun getAuthenticationUrl(): String {
        return Uri.parse(BASE_AUTH_URL).buildUpon()
                .appendPath("login")
                .appendQueryParameter("client_id", CLIENT_ID)
                .appendQueryParameter("response_type", AUTH_RESPONSE_TYPE)
                .appendQueryParameter("redirect_uri", OAUTH_REDIRECT_URI)
                .build()
                .toString()
    }

    fun getLogoutUrl(): String {
        return Uri.parse(BASE_AUTH_URL).buildUpon()
                .appendPath("logout")
                .appendQueryParameter("client_id", CLIENT_ID)
                .appendQueryParameter("response_type", AUTH_RESPONSE_TYPE)
                .appendQueryParameter("logout_uri", OAUTH_REDIRECT_URI)
                .build()
                .toString()
    }

}