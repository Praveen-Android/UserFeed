package com.praveen.userfeed.utils

import android.content.Context
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.graphics.drawable.StateListDrawable
import android.support.test.espresso.matcher.BoundedMatcher
import android.view.View
import android.widget.ImageView
import org.hamcrest.Description
import org.hamcrest.Matcher

object MatcherUtils {

    //View Matcher to get the image from an image view
    fun withImageDrawable(resourceId: Int): Matcher<View> {
        return object : BoundedMatcher<View, ImageView>(ImageView::class.java) {
            override fun describeTo(description: Description) {
                description.appendText("has image drawable resource $resourceId")
            }

            public override fun matchesSafely(imageView: ImageView): Boolean {
                return sameBitmap(imageView.context, imageView.drawable, resourceId)
            }
        }
    }

    //View Assertion of the bitmaps
    private fun sameBitmap(context: Context, drawable: Drawable?, resourceId: Int): Boolean {
        var drawableImage = drawable
        var otherDrawable: Drawable? = context.resources.getDrawable(resourceId, null)
        if (drawableImage == null || otherDrawable == null) {
            return false
        }
        if (drawableImage is StateListDrawable && otherDrawable is StateListDrawable) {
            drawableImage = drawableImage.current
            otherDrawable = otherDrawable.current
        }
        if (drawableImage is BitmapDrawable) {
            val bitmap = drawableImage.bitmap
            val otherBitmap = (otherDrawable as BitmapDrawable).bitmap
            return bitmap.sameAs(otherBitmap)
        }
        return false
    }
}