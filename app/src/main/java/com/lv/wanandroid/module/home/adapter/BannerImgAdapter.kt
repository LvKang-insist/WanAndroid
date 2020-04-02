package com.lv.wanandroid.module.home.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatImageView
import androidx.recyclerview.widget.RecyclerView
import com.lv.core.utils.glideLoad
import com.lv.wanandroid.R
import com.lv.wanandroid.module.home.bean.Data
import com.youth.banner.adapter.BannerAdapter

class BannerImgAdapter(bannerData: List<Data>) :
    BannerAdapter<Data, BannerImgAdapter.ImgHolder>(bannerData) {

    override fun onCreateHolder(parent: ViewGroup, viewType: Int): ImgHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.home_banner_iamge, parent, false)
        return ImgHolder(view)
    }

    override fun onBindView(holder: ImgHolder, data: Data, position: Int, size: Int) {
        glideLoad(data.imagePath, holder.image)
    }

    class ImgHolder(val view: View) : RecyclerView.ViewHolder(view) {
        val image: AppCompatImageView = view.findViewById(R.id.home_banner_image)
    }
}