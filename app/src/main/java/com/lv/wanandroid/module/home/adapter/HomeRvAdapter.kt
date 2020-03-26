package com.lv.wanandroid.module.home.adapter

import android.graphics.Color
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.lv.core.utils.longForDate
import com.lv.wanandroid.R
import com.lv.wanandroid.module.home.bean.Article

/**
 * @name WanAndroid-kotlin
 * @class name：com.lv.wanandroid.module.home.adapter
 * @author 345 QQ:1831712732
 * @time 2020/3/25 21:23
 * @description
 */

class HomeRvAdapter(layoutResId: Int) : BaseQuickAdapter<Article, BaseViewHolder>(layoutResId) {

    override fun convert(helper: BaseViewHolder, item: Article?) {
        if (item != null) {
            if (!item.fresh) {
                helper.setBackgroundColor(R.id.home_rv_item_fresh_tv, Color.WHITE)
                helper.setTextColor(R.id.home_rv_item_fresh_tv, Color.BLACK)
                helper.setText(R.id.home_rv_item_fresh_tv, item.chapterName)
            } else {
                helper.setBackgroundRes(R.id.home_rv_item_fresh_tv, R.drawable.shape_stick_gb)
                helper.setTextColor(R.id.home_rv_item_fresh_tv, Color.RED)
                helper.setText(R.id.home_rv_item_fresh_tv, "置顶")
            }
            helper.setText(R.id.home_rv_item_title_tv, item.title)
            helper.setText(R.id.home_rv_item_author_tv, item.author)
            helper.setText(R.id.home_rv_item_date_tv, longForDate(item.publishTime))
            helper.setText(R.id.home_rv_item_type_tv, item.superChapterName)
        }
    }
}