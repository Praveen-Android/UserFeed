package com.praveen.userfeed.ui.feeddetails

import android.os.Bundle
import com.praveen.userfeed.R
import com.praveen.userfeed.data.PhotoDetails
import com.praveen.userfeed.ui.BaseActivity
import com.praveen.userfeed.utils.AppConstants.INTENT_EXTRA_PHOTO_DETAILS

class PhotoDetailActivity: BaseActivity() {
    companion object {
        const val PHOTO_DETAIL_FRAGMENT_TAG = "PHOTO_DETAIL_FRAG"
    }

    private var mPhotoDetailFragment: PhotoDetailFragment? = null

    private var mPhotoDetails: PhotoDetails? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.layout_main)
        supportActionBar?.title = getString(R.string.photo_detail_title)

        mPhotoDetails = intent?.getParcelableExtra(INTENT_EXTRA_PHOTO_DETAILS)

        mPhotoDetailFragment = supportFragmentManager.findFragmentByTag(PHOTO_DETAIL_FRAGMENT_TAG) as? PhotoDetailFragment
        mPhotoDetailFragment?:let { mPhotoDetailFragment = mPhotoDetails?.let { details -> PhotoDetailFragment.newInstance(details) } }

        showPhotoDetailFragment()
    }

    private fun showPhotoDetailFragment() {
        mPhotoDetailFragment?.let {
            addFragmentToActivity(fragmentManager = supportFragmentManager, fragment = it,
                    frameId = R.id.activity_root, tag = PHOTO_DETAIL_FRAGMENT_TAG)
        }
    }
}