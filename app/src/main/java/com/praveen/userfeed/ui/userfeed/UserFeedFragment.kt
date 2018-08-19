package com.praveen.userfeed.ui.userfeed

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.*
import android.view.View.INVISIBLE
import android.view.View.VISIBLE
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.praveen.userfeed.R
import com.praveen.userfeed.UserFeedApplication
import com.praveen.userfeed.data.PhotoDetails
import com.praveen.userfeed.ui.UserFeedViewModelFactory
import com.praveen.userfeed.ui.auth.AuthenticationActivity
import com.praveen.userfeed.ui.data.PhotoStatus
import com.praveen.userfeed.ui.data.Response
import com.praveen.userfeed.ui.data.Status
import com.praveen.userfeed.ui.data.authStatus
import com.praveen.userfeed.ui.feeddetails.PhotoDetailActivity
import com.praveen.userfeed.utils.AppConstants.INTENT_EXTRA_AUTHENTICATION
import com.praveen.userfeed.utils.AppConstants.INTENT_EXTRA_PHOTO_DETAILS
import com.praveen.userfeed.utils.AppConstants.PAYLOAD_LIKE
import com.praveen.userfeed.utils.AppConstants.PAYLOAD_UNLIKE
import kotlinx.android.synthetic.main.layout_profile_photo.view.*
import kotlinx.android.synthetic.main.layout_userfeed.*
import java.util.Collections.sort
import javax.inject.Inject

class UserFeedFragment: Fragment(), PhotoListener {

    companion object {
        const val LOGIN_REQUEST_CODE = 1001
        const val LOGOUT_REQUEST_CODE = 1002
        const val PHOTO_DETAILS_REQUEST_CODE = 1003
        const val PLACEHOLDER_URL = "https://s3-us-west-2.amazonaws.com/images-23-pete/vp/8bab01a2b4487f7a64203271a8a42a63/5B3CE9A3/t51.2885-15/s320x320/e35/14515809_1721026988223961_1127353186336636928_n.jpg"
    }

    @Inject
    lateinit var mViewModelFactory: UserFeedViewModelFactory

    private lateinit var mViewModel: UserFeedViewModel

    private var mPhotosList: List<PhotoDetails> = mutableListOf()

    // user feed adapter position that is being updated (updates to like/unlike)
    private var mPosition: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)

        UserFeedApplication.appComponent.inject(this)
        mViewModel = ViewModelProviders.of(this, mViewModelFactory).get(UserFeedViewModel::class.java)
        mViewModel.response().observe(this, Observer { response -> processResponse(response!!) })
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.layout_userfeed, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if(mViewModel.getAuthorizationToken() == null){
            showLoginView()
        }else{
            showUserFeedView()
            mViewModel.fetchUserFeed()
        }
    }

    private fun showLoginView(){
        login_view.visibility = VISIBLE
        photo_feed_view.visibility = INVISIBLE
        progress_view.visibility = INVISIBLE
    }

    private fun showUserFeedView(){
        login_view.visibility = INVISIBLE
        photo_feed_view.visibility = VISIBLE
        progress_view.visibility = VISIBLE
    }

    override fun onCreateOptionsMenu(menu: Menu?, inflater: MenuInflater?) {
        if(mViewModel.getAuthorizationToken() == null){
            inflater?.inflate(R.menu.menu_login, menu)
        } else {
            inflater?.inflate(R.menu.menu_profile, menu)
        }
    }

    override fun onPrepareOptionsMenu(menu: Menu?) {
        val item = menu?.findItem(R.id.logout)
        item?.isVisible?:return

        if (mPhotosList.isNotEmpty()) {
            activity?.let {
                Glide.with(it)
                        //.load(mPhotosList[0].userProfilePicUrl)
                        .load(PLACEHOLDER_URL)
                        .apply(RequestOptions.circleCropTransform())
                        .into(item.actionView.profile_picture_view)
            }
        } else {
            //TODO add correct place holder
            activity?.let {
                Glide.with(it)
                        .load(PLACEHOLDER_URL)
                        .apply(RequestOptions.circleCropTransform())
                        .into(item.actionView.profile_picture_view)
            }
        }

        // A menu Item action view does not get the options menu item click event by default and so routing the action view click event manually to onOptionsItemSelected
        item.actionView.profile_picture_view.setOnClickListener { onOptionsItemSelected(item) }

        super.onPrepareOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        return when (item?.itemId) {
            R.id.login -> {
                val authenticationIntent = Intent(activity, AuthenticationActivity::class.java)
                authenticationIntent.apply { authenticationIntent.putExtra(INTENT_EXTRA_AUTHENTICATION, authStatus.LOGIN.toString()) }
                startActivityForResult(authenticationIntent, LOGIN_REQUEST_CODE)
                true
            }
            R.id.logout -> {
                mViewModel.setAuthorizationToken(token = null)
                val authenticationIntent = Intent(activity, AuthenticationActivity::class.java)
                authenticationIntent.apply { authenticationIntent.putExtra(INTENT_EXTRA_AUTHENTICATION, authStatus.LOGOUT.toString()) }
                startActivityForResult(authenticationIntent, LOGOUT_REQUEST_CODE)
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        activity?.invalidateOptionsMenu()
        when(requestCode){
            LOGIN_REQUEST_CODE -> {
                showUserFeedView()
                mViewModel.fetchUserFeed()
            }
            LOGOUT_REQUEST_CODE -> {
                showLoginView()
            }
            PHOTO_DETAILS_REQUEST_CODE -> {
                mViewModel.fetchUserFeed()
            }
        }
        super.onActivityResult(requestCode, resultCode, data)
    }

    //call back to handle a photo like/unlike event
    override fun updatePhotoFavoriteStatus(mediaId: String, photoStatus: PhotoStatus, position: Int) {
        mPosition = position
        when(photoStatus) {
            PhotoStatus.LIKE -> mViewModel.postUserLikesPhoto(mediaId)
            PhotoStatus.UNLIKE -> mViewModel.postUserUnlikesPhoto(mediaId)
        }
    }

    //call back to handle a photo click event
    override fun onPhotoClicked(photoDetails: PhotoDetails) {
        val photoDetailIntent = Intent(activity, PhotoDetailActivity::class.java)
        photoDetailIntent.apply { photoDetailIntent.putExtra(INTENT_EXTRA_PHOTO_DETAILS, photoDetails) }
        startActivityForResult(photoDetailIntent, PHOTO_DETAILS_REQUEST_CODE)
    }

    private fun processResponse(response: Response){
        when(response.status) {
            Status.LOADING -> progress_view.visibility = View.VISIBLE

            Status.SUCCESS -> handleSuccessResponse(response)

            Status.ERROR -> handleErrorResponse(response)
        }
    }

    //handle api success response
    private fun handleSuccessResponse(response:Response){
        progress_view.visibility = View.INVISIBLE
        when(response.data) {
            is List<*> -> updateUI(response.data as List<PhotoDetails>);
        }
    }

    //prepare the recycler view layout, adapter and render
    private fun updateUI(photosList: List<PhotoDetails>){
        mPhotosList = photosList
        val mLayoutManager: RecyclerView.LayoutManager = GridLayoutManager(activity, 2)
        photo_feed_view.layoutManager = mLayoutManager
        photo_feed_view.itemAnimator = DefaultItemAnimator()

        sort(photosList) {
            o1, o2 -> o2.createdTime.toInt().minus(o1.createdTime.toInt())
        }

        photo_feed_view.adapter = UserFeedAdapter(this, photosList)
    }

    /**
     * Handle api error response. In case of a photo like/unlike event, rollback the change since posting the same was unsuccessful
     */
    private fun handleErrorResponse(response: Response){
        progress_view.visibility = View.INVISIBLE
        mViewModel.setAuthorizationToken(token = null)
        showLoginView()
        activity?.invalidateOptionsMenu()

        when(response.data){
            is PhotoStatus -> {
                if (mPhotosList.get(mPosition).userHasLiked) {
                    mPhotosList.get(mPosition).userHasLiked = true
                    photo_feed_view.adapter.notifyItemChanged(mPosition, PAYLOAD_LIKE)
                } else {
                    mPhotosList.get(mPosition).userHasLiked = false
                    photo_feed_view.adapter.notifyItemChanged(mPosition, PAYLOAD_UNLIKE)
                }
            }
        }
    }
}