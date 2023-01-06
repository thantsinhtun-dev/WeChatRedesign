package com.stone.wechat.viewholder

import android.graphics.Bitmap
import android.util.TypedValue
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.SimpleTarget
import com.bumptech.glide.request.transition.Transition
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.source.*
import com.google.android.exoplayer2.upstream.DataSource
import com.google.android.exoplayer2.upstream.DefaultDataSource
import com.snov.timeagolibrary.PrettyTimeAgo
import com.stone.wechat.R
import com.stone.wechat.adapter.MomentContentAdapter
import com.stone.wechat.data.vos.MomentVO
import com.stone.wechat.delegates.MomentDelegate
import kotlinx.android.synthetic.main.view_holder_moments.view.*


class MomentViewHolder(itemView: View, mDelegate: MomentDelegate) :
    RecyclerView.ViewHolder(itemView) {

    private var mMomentContentAdapter: MomentContentAdapter = MomentContentAdapter()
    private var mMomentVO: MomentVO? = null

    init {
        itemView.imgMomentLike.setOnClickListener {
            mMomentVO?.let { vo ->
                mDelegate.onTapLike(vo, absoluteAdapterPosition)
            }
        }


        itemView.imgMomentBookMark.setOnClickListener {
            mMomentVO?.let { vo ->
                mDelegate.onTapBookMark(vo, absoluteAdapterPosition)
            }
        }

    }
    fun bindLikeAndLikeCount(mData: MomentVO){
        if (mData.isLiked) {
            itemView.imgMomentLike.setImageResource(R.drawable.ic_enable_like)
        } else itemView.imgMomentLike.setImageResource(R.drawable.ic_disable_like)
        itemView.lblMomentLikeCount.text = mData.likeCount.toString()
    }

    fun bindData(mData: MomentVO) {
        this.mMomentVO = mData
        itemView.playerMoment.visibility = View.GONE
        itemView.rvMomentContent.visibility = View.GONE
        itemView.imgMoment.visibility = View.GONE
        itemView.lblUserName.text = mData.userName
        itemView.lblMomentText.text = mData.momentText
        itemView.lblMomentTime.text = mData.time?.let { PrettyTimeAgo.getTimeAgo(it) }


        if (mData.isLiked) {
            itemView.imgMomentLike.setImageResource(R.drawable.ic_enable_like)
        } else itemView.imgMomentLike.setImageResource(R.drawable.ic_disable_like)
        itemView.lblMomentLikeCount.text = mData.likeCount.toString()





        Glide.with(itemView.context)
            .load(mData.profileImage)
            .placeholder(R.drawable.ic_avator)
            .into(itemView.imgProfile)

        if (mData.isMovie) {
            itemView.playerMoment.visibility = View.VISIBLE
            setUpPlayer(mData.content?.firstOrNull().toString(), itemView)
        } else {
            mData.imageList?.let { images ->
                if (images.size > 1) {
                    itemView.rvMomentContent.visibility = View.VISIBLE
                    itemView.rvMomentContent.adapter = mMomentContentAdapter
                    mMomentContentAdapter.setNewData(images, mData.isMovie)
                } else {
                    itemView.imgMoment.visibility = View.VISIBLE
                    Glide.with(itemView.context)
                        .asBitmap()
                        .load(mData.imageList?.firstOrNull().toString())
                        .into(object : SimpleTarget<Bitmap>() {
                            override fun onResourceReady(
                                resource: Bitmap,
                                transition: Transition<in Bitmap>?
                            ) {
                                if (resource.width > resource.height) {
                                    val dimensionInDp = TypedValue.applyDimension(
                                        TypedValue.COMPLEX_UNIT_DIP,
                                        350F,
                                        itemView.context.resources.displayMetrics
                                    ).toInt()

                                    itemView.imgMoment.layoutParams.width = dimensionInDp
                                    itemView.imgMoment.requestLayout()

                                }
                                itemView.imgMoment.setImageBitmap(resource)
                            }
                        })
                }
            }

        }
    }

    private fun setUpPlayer(videoLink: String, itemView: View) {
        val mediaDataSourceFactory: DataSource.Factory = DefaultDataSource.Factory(itemView.context)

        val mediaSource = ProgressiveMediaSource.Factory(mediaDataSourceFactory)
            .createMediaSource(MediaItem.fromUri(videoLink))


        val mediaSourceFactory = DefaultMediaSourceFactory(mediaDataSourceFactory)


        val simpleExoPlayer = ExoPlayer.Builder(itemView.context)
            .setMediaSourceFactory(mediaSourceFactory)
            .build()




        simpleExoPlayer.addMediaSource(mediaSource)

        simpleExoPlayer.playWhenReady = true
//        binding.playerView.player = simpleExoPlayer
//        binding.playerView.requestFocus()

        itemView.playerMoment.player = simpleExoPlayer
        itemView.playerMoment.requestFocus()
//        val videoSize = simpleExoPlayer.videoSize
//        Log.i("video_size",videoSize.width.toString().plus("  --   ").plus(videoSize.height))
//        if (videoSize.width > videoSize.height){
//            itemView.playerMoment.resizeMode = AspectRatioFrameLayout.RESIZE_MODE_FIXED_WIDTH
//        }else itemView.playerMoment.resizeMode = AspectRatioFrameLayout.RESIZE_MODE_FIT
    }
}