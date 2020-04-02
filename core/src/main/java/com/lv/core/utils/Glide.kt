package com.lv.core.utils

import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.lv.core.R

/**
 * 加载图片
 */
fun <T> T.glideLoad(url: String, imageView: ImageView) {
    Glide.with(imageView.context)
        .load(url)
        .error(R.drawable.error_img)
        .diskCacheStrategy(DiskCacheStrategy.ALL)
        .into(imageView)
}

/**
 * 清除缓存的图片，视图没有使用 setTag 时有效
 */
fun <T> T.glideClear(imageView: ImageView) {
    Glide.with(imageView.context).clear(imageView)
}
