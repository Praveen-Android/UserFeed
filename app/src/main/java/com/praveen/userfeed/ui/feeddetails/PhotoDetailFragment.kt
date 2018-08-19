package com.praveen.userfeed.ui.feeddetails

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.bumptech.glide.Glide
import com.praveen.userfeed.R
import com.praveen.userfeed.UserFeedApplication
import com.praveen.userfeed.data.PhotoDetails
import com.praveen.userfeed.ui.UserFeedViewModelFactory
import com.praveen.userfeed.ui.data.PhotoStatus
import com.praveen.userfeed.ui.data.Response
import com.praveen.userfeed.ui.data.Status
import com.praveen.userfeed.ui.userfeed.UserFeedViewModel
import com.praveen.userfeed.utils.pluralize
import kotlinx.android.synthetic.main.layout_photo_details.*
import javax.inject.Inject

class PhotoDetailFragment: Fragment() {

    @Inject
    lateinit var mViewModelFactory: UserFeedViewModelFactory

    private lateinit var mViewModel: UserFeedViewModel

    companion object {
        private const val ARG_PHOTO_DETAIL = "photo_details"

        fun newInstance(photoDetails: PhotoDetails): PhotoDetailFragment {
            val args = Bundle()
            args.putParcelable(ARG_PHOTO_DETAIL, photoDetails)
            val fragment = PhotoDetailFragment()
            fragment.arguments = args
            return fragment
        }
    }

    private var mPhotoDetails: PhotoDetails? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        UserFeedApplication.appComponent.inject(this)

        mViewModel = ViewModelProviders.of(this, mViewModelFactory).get(UserFeedViewModel::class.java)
        mViewModel.response().observe(this, Observer { response -> processResponse(response!!) })

        mPhotoDetails = arguments?.getParcelable(ARG_PHOTO_DETAIL)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.layout_photo_details, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        activity?.let {
            Glide.with(it).load(mPhotoDetails?.standardImageUrl).into(photo_view)
            when (mPhotoDetails?.userHasLiked) {
                true -> photo_fav_button.background = it.getDrawable(R.drawable.ic_like)
                false -> photo_fav_button.background = it.getDrawable(R.drawable.ic_unlike)
            }
        }

        val likesCount = mPhotoDetails?.likesCount?:0
        photo_fav_count_view.text = likesCount.toString().plus(" ").plus(activity?.getString(R.string.photo_like_text)?.pluralize(likesCount))
        creation_date_view.text = mPhotoDetails?.relativeTime

        photo_fav_button.setOnClickListener {
            //update the UI immediately and make a call to post/delete based on like/unlike event respectively
            var count = mPhotoDetails?.likesCount?:0

            activity?.let {
                when (mPhotoDetails?.userHasLiked) {

                    true -> {
                        mPhotoDetails?.userHasLiked = false
                        count = count.dec()
                        mPhotoDetails?.mediaId?.let { id -> mViewModel.postUserUnlikesPhoto(id) }
                        photo_fav_button.background = it.getDrawable(R.drawable.ic_unlike)
                    }
                    else -> {
                        mPhotoDetails?.userHasLiked = true
                        count = count.inc()
                        mPhotoDetails?.mediaId?.let { id -> mViewModel.postUserLikesPhoto(id) }
                        photo_fav_button.background = it.getDrawable(R.drawable.ic_like)
                    }
                }
                mPhotoDetails?.likesCount = count
                photo_fav_count_view.text = count.toString().plus(" ").plus(it.getString(R.string.photo_like_text)?.pluralize(count))

            }
        }
    }

    private fun processResponse(response: Response) {
        when (response.status) {
            Status.SUCCESS -> handleSuccessResponse(response)
            else -> handleErrorResponse(response)
        }
    }

    private fun handleSuccessResponse(response: Response) {
        if(response.data is PhotoStatus) {
            //do nothing since we already updated the UI
        }
    }

    /**
     * Handle api ]error response. In case of a photo like/unlike event, rollback the change since posting the same was unsuccessful
     *
     */
    private fun handleErrorResponse(response: Response) {
        var count = mPhotoDetails?.likesCount ?: 0
        activity?.let {
            Toast.makeText(it, getString(R.string.selection_update_error), Toast.LENGTH_SHORT).show()
            when (mPhotoDetails?.userHasLiked) {
                true -> {
                    mPhotoDetails?.userHasLiked = false
                    count = count.dec()
                    photo_fav_button.background = it.getDrawable(R.drawable.ic_unlike)
                }
                else -> {
                    mPhotoDetails?.userHasLiked = true
                    count = count.inc()
                    photo_fav_button.background = it.getDrawable(R.drawable.ic_like)
                }
            }

            mPhotoDetails?.likesCount = count
            photo_fav_count_view.text = count.toString().plus(" ").plus(it.getString(R.string.photo_like_text)?.pluralize(count))
        }
    }
}