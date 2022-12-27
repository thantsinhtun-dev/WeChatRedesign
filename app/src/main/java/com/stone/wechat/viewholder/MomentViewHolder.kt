package com.stone.wechat.viewholder

import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.os.Handler
import android.util.Log
import android.util.TypedValue
import android.view.View
import androidx.annotation.Nullable
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.SimpleTarget
import com.bumptech.glide.request.target.Target
import com.bumptech.glide.request.transition.Transition
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.source.*
import com.google.android.exoplayer2.ui.AspectRatioFrameLayout
import com.google.android.exoplayer2.upstream.DataSource
import com.google.android.exoplayer2.upstream.DefaultDataSource
import com.snov.timeagolibrary.PrettyTimeAgo
import com.stone.wechat.adapter.MomentContentAdapter
import com.stone.wechat.data.vos.MomentVO
import kotlinx.android.synthetic.main.view_holder_moments.view.*


class MomentViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    private var mMomentContentAdapter: MomentContentAdapter = MomentContentAdapter()

    fun bindData(mData: MomentVO) {
        itemView.playerMoment.visibility = View.GONE
        itemView.rvMomentContent.visibility = View.GONE
        itemView.imgMoment.visibility = View.GONE


        itemView.lblUserName.text = mData.userName
        itemView.lblMomentText.text = mData.momentText
        itemView.lblMomentTime.text = PrettyTimeAgo.getTimeAgo(mData.time)

        if(mData.isMovie){
            itemView.playerMoment.visibility = View.VISIBLE
            setUpPlayer(mData.content?.firstOrNull().toString(),itemView)
        } else if (!mData.imageList.isNullOrEmpty()) {
            if (mData.imageList.size > 1) {
                itemView.rvMomentContent.visibility = View.VISIBLE
                itemView.rvMomentContent.adapter = mMomentContentAdapter
                mMomentContentAdapter.setNewData(mData.imageList, mData.isMovie)
            }else{
                itemView.imgMoment.visibility = View.VISIBLE
                Glide.with(itemView.context)
                    .asBitmap()
                    .load(mData.imageList.firstOrNull().toString())
                    .into(object : SimpleTarget<Bitmap>(){
                        override fun onResourceReady(
                            resource: Bitmap,
                            transition: Transition<in Bitmap>?
                        ) {
                            if(resource.width > resource.height){
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
    private fun setUpPlayer(videoLink: String, itemView: View) {
        val mediaDataSourceFactory: DataSource.Factory = DefaultDataSource.Factory(itemView.context)

        val mediaSource = ProgressiveMediaSource.Factory(mediaDataSourceFactory)
            .createMediaSource(MediaItem.fromUri(videoLink))



        val mediaSourceFactory = DefaultMediaSourceFactory(mediaDataSourceFactory)


        val simpleExoPlayer = ExoPlayer.Builder(itemView.context)
            .setMediaSourceFactory(mediaSourceFactory)
            .build()

        val videoSize = simpleExoPlayer.videoSize
        if (videoSize.width > videoSize.height){
            itemView.playerMoment.resizeMode = AspectRatioFrameLayout.RESIZE_MODE_FIXED_WIDTH
        }else itemView.playerMoment.resizeMode = AspectRatioFrameLayout.RESIZE_MODE_FIT


        simpleExoPlayer.addMediaSource(mediaSource)

        simpleExoPlayer.playWhenReady = true
//        binding.playerView.player = simpleExoPlayer
//        binding.playerView.requestFocus()

        itemView.playerMoment.player = simpleExoPlayer
        itemView.playerMoment.requestFocus()
    }
}