package com.lv.core.utils

import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.lv.core.R

fun <T> T.load(url: String, imageView: ImageView) {
    Glide.with(imageView.context)
        .load(url)
        .error(R.drawable.error_img)
        .diskCacheStrategy(DiskCacheStrategy.ALL)
        .into(imageView)
}