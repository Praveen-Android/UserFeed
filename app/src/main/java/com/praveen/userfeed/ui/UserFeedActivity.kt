package com.praveen.userfeed.ui

import android.os.Bundle
import com.praveen.userfeed.R
import com.praveen.userfeed.ui.userfeed.UserFeedFragment

class UserFeedActivity : BaseActivity() {

    companion object {
        const val USER_FEED_FRAGMENT_TAG = "USER_FEED_FRAG"
    }

    private var mUserFeedFragment: UserFeedFragment? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.layout_main)
        supportActionBar?.setDisplayHomeAsUpEnabled(false)

        mUserFeedFragment = supportFragmentManager.findFragmentByTag(USER_FEED_FRAGMENT_TAG) as? UserFeedFragment
        mUserFeedFragment?:let { mUserFeedFragment = UserFeedFragment() }

        showUserFeedFragment()
    }

    private fun showUserFeedFragment() {
        mUserFeedFragment?.let {
            addFragmentToActivity(fragmentManager = supportFragmentManager, fragment = it,
                frameId = R.id.activity_root, tag = USER_FEED_FRAGMENT_TAG)
        }
    }
}
