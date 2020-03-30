package com.lv.wanandroid.module.system.adapter

import android.graphics.Color
import android.view.View
import androidx.appcompat.widget.AppCompatTextView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.lv.wanandroid.R
import com.lv.wanandroid.module.system.bean.TreeBean

class RvRightAdapterContent(layoutResId: Int) :
    BaseQuickAdapter<TreeBean.Data.Children, BaseViewHolder>(layoutResId) {
    override fun convert(helper: BaseViewHolder, item: TreeBean.Data.Children?) {
        helper.setText(R.id.system_right_tv, item?.name)
    }
}
