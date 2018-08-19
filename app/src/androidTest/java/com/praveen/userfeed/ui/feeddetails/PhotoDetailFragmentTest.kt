package com.praveen.userfeed.ui.feeddetails

import android.content.Context
import android.content.Intent
import android.support.test.InstrumentationRegistry
import android.support.test.espresso.Espresso.onView
import android.support.test.espresso.action.ViewActions.click
import android.support.test.espresso.assertion.ViewAssertions.matches
import android.support.test.espresso.matcher.ViewMatchers.*
import android.support.test.rule.ActivityTestRule
import android.support.test.runner.AndroidJUnit4
import com.praveen.userfeed.R
import com.praveen.userfeed.data.PhotoDetails
import com.praveen.userfeed.utils.AppConstants
import com.praveen.userfeed.utils.TestUtils
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
open class PhotoDetailFragmentTest {

    private var mContext: Context? = null

    private var mPhotoDetails:PhotoDetails? = null

    @get:Rule
    val activityTestRule: ActivityTestRule<PhotoDetailActivity> = ActivityTestRule(PhotoDetailActivity::class.java, true, false)

    @Before
    @Throws(Exception::class)
    fun setUp() {
        mContext = InstrumentationRegistry.getTargetContext()
        mPhotoDetails = TestUtils.getSamplePhotoDetails()
    }

    @After
    @Throws(Exception::class)
    fun tearDown() {
        mContext = null
        mPhotoDetails = null
    }

    private fun launchActivity() {
        val intent = Intent(mContext, PhotoDetailActivity::class.java)
        intent.putExtra(AppConstants.INTENT_EXTRA_PHOTO_DETAILS, mPhotoDetails)
        activityTestRule.launchActivity(intent)
    }

    @Test
    fun verifyAllDisplayedData() {
        launchActivity()
        onView(withText(mPhotoDetails?.relativeTime)).check(matches(isDisplayed()))
        onView(withId(R.id.creation_date_view)).check(matches(withText("21m ago")))
        onView(withId(R.id.photo_fav_count_view)).check(matches(withText("12 Likes")))
        //onView(withId(R.id.photo_view)).check(matches(MatcherUtils.withImageDrawable(R.drawable.ic_like)))
    }

    @Test
    fun verifyLikeUnlikeActions() {
        launchActivity()
        onView(withId(R.id.photo_fav_button)).check(matches(isDisplayed()))
        onView(withId(R.id.photo_fav_button)).check(matches(isEnabled()))

        //unlike action
        //onView(withId(R.id.photo_view)).check(matches(MatcherUtils.withImageDrawable(R.drawable.ic_like)))
        onView(withId(R.id.photo_fav_count_view)).check(matches(withText("12 Likes")))
        onView(withId(R.id.photo_fav_button)).perform(click());
        //onView(withId(R.id.photo_view)).check(matches(MatcherUtils.withImageDrawable(R.drawable.ic_unlike)))
        onView(withId(R.id.photo_fav_count_view)).check(matches(withText("11 Likes")))

        //like action
        //FIXME: need to check on how to match vector drawables using matchers
        //onView(withId(R.id.photo_view)).check(matches(MatcherUtils.withImageDrawable(R.drawable.ic_unlike)))
        onView(withId(R.id.photo_fav_count_view)).check(matches(withText("11 Likes")))
        onView(withId(R.id.photo_fav_button)).perform(click());
        //onView(withId(R.id.photo_view)).check(matches(MatcherUtils.withImageDrawable(R.drawable.ic_like)))
        onView(withId(R.id.photo_fav_count_view)).check(matches(withText("12 Likes")))
    }


}