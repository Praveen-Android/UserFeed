package com.praveen.userfeed.ui.userfeed

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.praveen.userfeed.R
import com.praveen.userfeed.UserFeedApplication
import com.praveen.userfeed.data.PhotoDetails
import com.praveen.userfeed.ui.data.PhotoStatus
import com.praveen.userfeed.utils.AppConstants.PAYLOAD_LIKE
import com.praveen.userfeed.utils.AppConstants.PAYLOAD_UNLIKE
import com.praveen.userfeed.utils.pluralize
import javax.inject.Inject

class UserFeedAdapter(private val listener: PhotoListener, private val list: List<PhotoDetails>) : RecyclerView.Adapter<UserFeedAdapter.UserFeedViewHolder>() {

    @Inject
    lateinit var mContext: Context

    init {
        UserFeedApplication.appComponent.inject(this)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserFeedViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.layout_photo_card, parent, false)
        return UserFeedViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: UserFeedViewHolder, position: Int, payloads: MutableList<Any>) {
        var count = 0

        if(!payloads.isEmpty()){

            if(payloads.contains(PAYLOAD_LIKE)){
                holder.favButton.background = mContext.getDrawable(R.drawable.ic_like)
                count = list[position].likesCount.inc()

            }else if(payloads.contains(PAYLOAD_UNLIKE)){
                holder.favButton.background = mContext.getDrawable(R.drawable.ic_unlike)
                count = list[position].likesCount.dec()
            }

            list[position].likesCount = count
            holder.favCountText.text = count.toString().plus(" ").plus(mContext.getString(R.string.photo_like_text).pluralize(count))

        } else {
            super.onBindViewHolder(holder, position, payloads)
        }
    }

    override fun onBindViewHolder(holder: UserFeedViewHolder, position: Int) {
        val item = list[position]

        Glide.with(mContext).load(item.standardImageUrl).into(holder.feedPhoto)

        if (item.userHasLiked) {
            holder.favButton.background = mContext.getDrawable(R.drawable.ic_like)
        } else {
            holder.favButton.background = mContext.getDrawable(R.drawable.ic_unlike)
        }

        holder.favCountText.text = item.likesCount.toString().plus(" ").plus(mContext.getString(R.string.photo_like_text).pluralize(item.likesCount))


        holder.favButton.setOnClickListener {
            //update the UI immediately and make a call to post/delete based on like/unlike event respectively
            when (item.userHasLiked) {
                true -> {
                    listener.updatePhotoFavoriteStatus(item.mediaId, PhotoStatus.UNLIKE,  holder.adapterPosition)
                    item.userHasLiked = false
                    notifyItemChanged(position, PAYLOAD_UNLIKE)
                }
                else -> {
                    listener.updatePhotoFavoriteStatus(item.mediaId, PhotoStatus.LIKE,  holder.adapterPosition)
                    item.userHasLiked = true
                    notifyItemChanged(position, PAYLOAD_LIKE)
                }
            }
        }

        holder.feedPhoto.setOnClickListener {
            listener.onPhotoClicked(item)
        }
    }

    override fun getItemCount(): Int {
        return list.count()
    }

    class UserFeedViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var feedPhoto: ImageView = view.findViewById(R.id.feed_photo)
        var favButton: ImageButton = view.findViewById(R.id.fav_button)
        var favCountText: TextView = view.findViewById(R.id.fav_count_text)

    }
}