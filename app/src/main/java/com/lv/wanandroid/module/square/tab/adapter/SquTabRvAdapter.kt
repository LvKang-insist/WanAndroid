package com.lv.wanandroid.module.square.tab.adapter

import android.content.Intent
import androidx.appcompat.widget.AppCompatTextView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.lv.core.utils.glideClear
import com.lv.core.utils.glideLoad
import com.lv.wanandroid.R
import com.lv.wanandroid.module.square.bean.Wxarticle
import com.lv.wanandroid.web.WebX5Activity

class SquTabRvAdapter(layoutResId: Int) :
    BaseQuickAdapter<Wxarticle.Data.Data, BaseViewHolder>(layoutResId) {


    override fun convert(helper: BaseViewHolder, item: Wxarticle.Data.Data?) {
        if (item != null) {
            helper.setText(R.id.tab_rv_title, item.title)
            helper.setText(R.id.tab_rv_author, item.author)
            helper.setText(R.id.tab_rv_date, item.niceDate)
            helper.getView<AppCompatTextView>(R.id.tab_rv_address).setOnClickListener {
                val intent = Intent(it.context, WebX5Activity::class.java)
                intent.putExtra("link", item.projectLink)
                it.context.startActivity(intent)
            }
            glideLoad(item.envelopePic, helper.getView(R.id.tab_rv_iv))
        }
    }

    override fun onViewRecycled(holder: BaseViewHolder) {
        glideClear(holder.getView(R.id.tab_rv_iv))
    }
}