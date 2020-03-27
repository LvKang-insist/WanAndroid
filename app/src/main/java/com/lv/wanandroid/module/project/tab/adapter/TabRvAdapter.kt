package com.lv.wanandroid.module.project.tab.adapter

import android.content.Intent
import androidx.appcompat.widget.AppCompatTextView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.lv.core.utils.load
import com.lv.wanandroid.R
import com.lv.wanandroid.module.project.bean.DataX
import com.lv.wanandroid.web.WebActivity

class TabRvAdapter(layoutResId: Int) : BaseQuickAdapter<DataX, BaseViewHolder>(layoutResId) {

    override fun convert(helper: BaseViewHolder, item: DataX?) {
        if (item != null) {
            helper.setText(R.id.tab_rv_title, item.title)
            helper.setText(R.id.tab_rv_author, item.author)
            helper.setText(R.id.tab_rv_date, item.niceDate)
            helper.getView<AppCompatTextView>(R.id.tab_rv_address).setOnClickListener {
                val intent = Intent(it.context, WebActivity::class.java)
                intent.putExtra("link", item.projectLink)
                it.context.startActivity(intent)
            }
            load(item.envelopePic, helper.getView(R.id.tab_rv_iv))
        }
    }

}