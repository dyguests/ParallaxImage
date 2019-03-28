package com.fanhl.parallaximage.sample

import android.graphics.drawable.Drawable
import android.widget.ImageView
import androidx.annotation.DrawableRes
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition

fun ImageView.setImage(
    url: String,
    @DrawableRes placeHolder: Int? = null
) {
    Glide.with(this)
        .load(url)
        .apply {
            if (placeHolder != null) {
                apply(RequestOptions().placeholder(placeHolder))
            }
        }
        .into(this)
}

fun ImageView.setImageAsDrawable(
    url: String,
    @DrawableRes placeHolder: Int? = null
) {
    Glide.with(this)
//        .asBitmap()
        .load(url)
        .apply {
            if (placeHolder != null) {
                apply(RequestOptions().placeholder(placeHolder))

            }
        }
        .into(object : CustomTarget<Drawable>() {
            override fun onLoadCleared(placeholder: Drawable?) {
                this@setImageAsDrawable.setImageDrawable(placeholder)
            }

            override fun onResourceReady(resource: Drawable, transition: Transition<in Drawable>?) {
                this@setImageAsDrawable.setImageDrawable(resource)
            }
        })
}
