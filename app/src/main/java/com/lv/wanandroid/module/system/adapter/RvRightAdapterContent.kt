package com.lv.wanandroid.module.system.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.AppCompatTextView
import com.lv.core.recycler.MultipleFields
import com.lv.core.recycler.MultipleItemEntity
import com.lv.core.recycler.MultipleRecyclerAdapter
import com.lv.core.recycler.MultipleViewHolder
import com.lv.core.view.WarpLinearLayout
import com.lv.wanandroid.R
import com.lv.wanandroid.list.ListActivity
import com.lv.wanandroid.module.system.bean.TreeBean

/**
 * 体系右半边 Rv的适配器
 */
class RvRightAdapterContent(data: List<MultipleItemEntity>) :
    MultipleRecyclerAdapter(data) {

    init {
        addItemType(SystemItemType.ITEM_TYPE_RIGHT_CONTENT, R.layout.system_right_layout_item)
        addItemType(SystemItemType.ITEM_TYPE_RIGHT_IMG, R.layout.system_right_img_item)
    }

    override fun convert(holder: MultipleViewHolder, entity: MultipleItemEntity?) {
        when (entity?.itemType) {
            SystemItemType.ITEM_TYPE_RIGHT_IMG -> {
                holder.setImageResource(R.id.system_right_content_img, R.drawable.item_img)
            }
            SystemItemType.ITEM_TYPE_RIGHT_CONTENT -> {
                val list = entity.getField<List<TreeBean.Data.Children>>(MultipleFields.LIST)
                val layout = holder.getView<WarpLinearLayout>(R.id.system_right_content_layout)
                holder.setText(
                    R.id.system_right_content_title, entity.getField<String>(MultipleFields.TEXT)
                )
                var i = 0
                list.forEach {
                    val linear = LayoutInflater.from(layout.context)
                        .inflate(R.layout.system_right_layout_content_item, layout, false)
                    val img = linear.findViewById<AppCompatImageView>(R.id.system_right_content_img)
                    val tv = linear.findViewById<AppCompatTextView>(R.id.system_right_content_tv)
                    img.setBackgroundResource(R.drawable.error_img)
                    tv.text = it.name
                    linear.setOnClickListener(object : View.OnClickListener {
                        val pos = i
                        override fun onClick(v: View?) {
                            val intent = Intent(layout.context, ListActivity::class.java)
                            intent.putExtra(ListActivity.KEY, ListActivity.SYSTEM_CONTENT_LIST)
                            intent.putExtra("id", list[pos].id)
                            intent.putExtra("name", list[pos].name)
                            layout.context.startActivity(intent)
                        }
                    })
                    layout.addView(linear)
                    ++i
                }
                //补空位
                if (list.size % 3 > 0) {
                    val line = list.size / 3
                    val i = 3 - (list.size - line * 3)
                    for (x in 1..i) {
                        val linear = LayoutInflater.from(layout.context)
                            .inflate(R.layout.system_right_layout_content_item, layout, false)
                        layout.addView(linear)
                    }
                }
            }
        }
    }

}
