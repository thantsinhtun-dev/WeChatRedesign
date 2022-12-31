package com.stone.wechat.viewholder

import android.graphics.Bitmap
import android.util.TypedValue
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.SimpleTarget
import com.bumptech.glide.request.transition.Transition
import kotlinx.android.synthetic.main.view_holder_moment_content.view.*

class MomentContentViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    fun bindData(mData: String, isMovie: Boolean) {


        Glide.with(itemView.context)
            .asBitmap()
            .load(mData)
            .into(object : SimpleTarget<Bitmap>() {
                override fun onResourceReady(
                    resource: Bitmap,
                    transition: Transition<in Bitmap>?
                ) {
                    if (resource.width > resource.height) {
                        val dimensionInDp = TypedValue.applyDimension(
                            TypedValue.COMPLEX_UNIT_DIP,
                            300F,
                            itemView.context.resources.displayMetrics
                        ).toInt()

                        itemView.imgMomentContent.layoutParams.width = dimensionInDp
                        itemView.imgMomentContent.requestLayout()

                    }
                    itemView.imgMomentContent.setImageBitmap(resource)
                }
            })


    }


}