package com.lv.wanandroid.module.system.list.adapter

import android.content.Intent
import androidx.appcompat.widget.LinearLayoutCompat
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.lv.wanandroid.R
import com.lv.wanandroid.module.system.list.bean.Article
import com.lv.wanandroid.web.AgentWebActivity


class ListRvAdapter(layoutResId: Int) :
    BaseQuickAdapter<Article.Data.Data, BaseViewHolder>(layoutResId) {

    override fun convert(helper: BaseViewHolder, item: Article.Data.Data?) {
        if (item != null) {
            helper.setText(R.id.activity_list_title, item.title)
            helper.setText(R.id.activity_list_author, item.author)
            helper.setText(R.id.activity_list_date, item.niceDate)
            helper.setText(R.id.activity_list_type, item.superChapterName)
        }
        val layout = helper.getView<LinearLayoutCompat>(R.id.activity_list_layout)
        layout.setOnClickListener {
            val intent = Intent(layout.context, AgentWebActivity::class.java)
            intent.putExtra("link", item?.link)
            layout.context.startActivity(intent)
        }
    }

}