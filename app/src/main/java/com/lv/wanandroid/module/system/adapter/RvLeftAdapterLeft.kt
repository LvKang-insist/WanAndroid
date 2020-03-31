package com.lv.wanandroid.module.system.adapter

import android.graphics.Color
import android.view.View
import androidx.appcompat.widget.AppCompatTextView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.lv.wanandroid.R
import com.lv.wanandroid.module.system.bean.TreeBean

class RvLeftAdapterLeft(layoutResId: Int) :
    BaseQuickAdapter<TreeBean.Data, BaseViewHolder>(layoutResId) {

    var listener: ((Int) -> Unit)? = null

    private var pos = 0

    override fun convert(helper: BaseViewHolder, item: TreeBean.Data?) {
        val text = helper.getView<AppCompatTextView>(R.id.system_lv_item_tv)
        val view = helper.getView<View>(R.id.system_lv_item_view)
        text.text = item?.name
        if (helper.adapterPosition == pos) {
            text.setTextColor(Color.RED)
            view.setBackgroundColor(Color.RED)
        } else {
            text.setTextColor(Color.BLACK)
            view.setBackgroundColor(Color.WHITE)
        }
        text.setOnClickListener {
            text.setTextColor(Color.RED)
            view.setBackgroundColor(Color.RED)
            notifyItemChanged(pos)
            pos = helper.adapterPosition
            if (listener != null) {
                listener?.let { it1 -> it1(pos) }
            }
        }
    }

    fun setPos(position: Int) {
        notifyItemChanged(pos)
        this.pos = position
        notifyItemChanged(pos)
    }
}
