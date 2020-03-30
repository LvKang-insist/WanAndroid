package com.lv.wanandroid.module.system.adapter

import android.view.LayoutInflater
import android.view.View
import androidx.appcompat.widget.AppCompatTextView
import androidx.appcompat.widget.LinearLayoutCompat
import androidx.core.widget.NestedScrollView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.elvishew.xlog.XLog
import com.lv.wanandroid.R
import com.lv.wanandroid.module.system.bean.TreeBean

/**
 * @name WanAndroid-kotlin
 * @class name：com.lv.wanandroid.module.system.adapter
 * @author 345 QQ:1831712732
 * @time 2020/3/28 20:49
 * @description
 */


class RvAdapterContent(layoutResId: Int) :
    BaseQuickAdapter<TreeBean.Data, RvAdapterContent.ViewHolder>(layoutResId) {
    override fun convert(helper: ViewHolder, item: TreeBean.Data?) {
        val layout = helper.getView<LinearLayoutCompat>(R.id.layout_sym_vp_page_linear)
        layout.removeAllViews()
        if (item != null) {
            item.children.forEach {
                XLog.e("${helper.adapterPosition} --- ${it.name} ------ ${item.children.size}")
                val text = LayoutInflater.from(layout.context).inflate(
                    R.layout.system_text_item, null, false
                ) as AppCompatTextView
                text.text = it.name
                layout.addView(text)
            }

            if (listener != null) {
                layout.setOnClickListener {
                    listener!!(helper.adapterPosition)
                }
            }
        }
    }

    var listener: ((Int) -> Unit?)? = null


    class ViewHolder(val view: View) : BaseViewHolder(view) {

        val scroll = view.findViewById<NestedScrollView>(R.id.layout_sym_vp_page_scroll)
        /**
         *
         * @param x event的rowX
         * @param y event的rowY
         * @return 这个点在不在sv的范围内.
         */
        fun isTouchNsv(x: Float, y: Float): Boolean {
            val pos = IntArray(2)
            scroll.getLocationOnScreen(pos)
            val width = scroll.measuredWidth
            val height = scroll.measuredHeight
            if (x >= pos[0] && x <= pos[0] + width && y >= pos[1] && y <= pos[1] + height) {

            }
            return true
        }
    }
}

//class RvAdapterContent : RecyclerView.Adapter<RvAdapterContent.ViewHolder>() {
//    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
//        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
//    }
//
//    override fun getItemCount(): Int {
//        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
//    }
//
//    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
//        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
//    }
//
//    class ViewHolder(val view:View) :RecyclerView.ViewHolder(view){
//
//    }
//}