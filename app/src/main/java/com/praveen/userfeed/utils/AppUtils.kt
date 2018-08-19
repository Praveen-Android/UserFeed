package com.praveen.userfeed.utils

import android.net.Uri


object AppUtils {

    private const val ACCESS_TOKEN_PARAM = "id_token="

    fun getAccessToken(uri: String): String? {
        val items = uri.split("&".toRegex(), 2)
        val oauthUri = Uri.parse(items[0])
        val uriFragment = oauthUri.fragment
        if (uriFragment.contains(ACCESS_TOKEN_PARAM)) {
            return uriFragment.substring(ACCESS_TOKEN_PARAM.length)
        }

        return null
    }
}