package com.lv.wanandroid.module.system.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatTextView
import androidx.appcompat.widget.LinearLayoutCompat
import androidx.recyclerview.widget.RecyclerView
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

class VpAdapter(val data: List<TreeBean.Data>) :
    RecyclerView.Adapter<VpAdapter.VpHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VpHolder {
        return VpHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.layout_system_page, parent, false
            )
        )
    }

    override fun getItemCount(): Int {
        return data.size
    }

    @SuppressLint("InflateParams")
    override fun onBindViewHolder(holder: VpHolder, position: Int) {
        val layout = LayoutInflater.from(holder.layout.context).inflate(
            R.layout.system_text_layout, null, false
        ) as LinearLayoutCompat
        for (i in data[position].children.indices) {
            val text = LayoutInflater.from(holder.layout.context).inflate(
                R.layout.system_text_item, null, false
            ) as AppCompatTextView
            text.text = data[position].children[i].name
            layout.addView(text)
        }
        holder.layout.addView(layout)
    }

    class VpHolder(val view: View) : RecyclerView.ViewHolder(view) {
        val layout: LinearLayoutCompat = view.findViewById(R.id.layout_sym_vp_page_linear)
    }

}