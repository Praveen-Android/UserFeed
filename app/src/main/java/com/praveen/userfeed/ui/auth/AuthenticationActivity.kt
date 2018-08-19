package com.praveen.userfeed.ui.auth

import android.annotation.SuppressLint
import android.annotation.TargetApi
import android.app.Activity
import android.os.Build
import android.os.Bundle
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient
import com.praveen.userfeed.UserFeedApplication
import com.praveen.userfeed.api.ApiEndPoint
import com.praveen.userfeed.prefs.PreferenceHelper
import com.praveen.userfeed.ui.BaseActivity
import com.praveen.userfeed.ui.data.authStatus
import com.praveen.userfeed.utils.AppConstants.INTENT_EXTRA_AUTHENTICATION
import com.praveen.userfeed.utils.AppUtils
import javax.inject.Inject

class AuthenticationActivity : BaseActivity() {

    @Inject
    lateinit var mPreferenceHelper: PreferenceHelper

    lateinit var mWebView: WebView

    var authRequest: String? = null

    @SuppressLint("SetJavaScriptEnabled")
    public override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mWebView = WebView(this)
        setContentView(mWebView)

        UserFeedApplication.appComponent.inject(this)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            mWebView.settings.safeBrowsingEnabled = false
        }

        mWebView.settings.javaScriptEnabled = true
        mWebView.webViewClient = mWebViewClient

        authRequest = intent.extras.getString(INTENT_EXTRA_AUTHENTICATION)
        authRequest?.let {
            when(it){
                authStatus.LOGIN.toString() -> mWebView.loadUrl(ApiEndPoint.getAuthenticationUrl())
                authStatus.LOGOUT.toString() -> mWebView.loadUrl(ApiEndPoint.getLogoutUrl())
                else -> finish()
            }
        }
    }

    private val mWebViewClient = object : WebViewClient() {

        override fun shouldOverrideUrlLoading(webView: WebView, url: String): Boolean {
            handleRedirect(url)
            return false
        }

        @TargetApi(Build.VERSION_CODES.N)
        override fun shouldOverrideUrlLoading(view: WebView, request: WebResourceRequest): Boolean {
            val url = request.url.toString()
            handleRedirect(url)
            return false
        }

        private fun handleRedirect(url: String) {
            if (authRequest.equals(authStatus.LOGIN.toString()) && url.contains(ApiEndPoint.OAUTH_REDIRECT_URI)) {
                val accessToken = AppUtils.getAccessToken(url)
                if (accessToken != null) {
                    // store Authorization token
                    mPreferenceHelper.setAuthorizationToken(accessToken)
                }
            }else if (authRequest.equals(authStatus.LOGOUT.toString())) {
                mPreferenceHelper.setAuthorizationToken(null)
            }

            setResult(Activity.RESULT_OK)
            finish()
        }
    }

    override fun onDestroy() {
        mWebView.clearCache(true)
        mWebView.clearHistory()
        super.onDestroy()
    }
}