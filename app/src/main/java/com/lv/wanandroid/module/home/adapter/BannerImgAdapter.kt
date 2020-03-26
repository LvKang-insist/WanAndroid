package com.lv.wanandroid.module.home.adapter

import android.view.ViewGroup
import android.widget.ImageView
import androidx.appcompat.widget.AppCompatImageView
import androidx.recyclerview.widget.RecyclerView
import com.lv.core.utils.load
import com.lv.wanandroid.module.home.bean.Data
import com.youth.banner.adapter.BannerAdapter

class BannerImgAdapter(bannerData: List<Data>) :
    BannerAdapter<Data, BannerImgAdapter.ImgHolder>(bannerData) {

    override fun onCreateHolder(parent: ViewGroup, viewType: Int): ImgHolder {
        val imgView = AppCompatImageView(parent.context)
        //注意，必须设置为match_parent，这个是viewpager2强制要求的
        imgView.layoutParams = ViewGroup.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.MATCH_PARENT
        )
        imgView.scaleType = ImageView.ScaleType.CENTER_CROP
        return ImgHolder(imgView)
    }

    override fun onBindView(holder: ImgHolder, data: Data, position: Int, size: Int) {
        load(data.imagePath, holder.imageView)
    }

    class ImgHolder(val imageView: AppCompatImageView) : RecyclerView.ViewHolder(imageView) {

    }
}