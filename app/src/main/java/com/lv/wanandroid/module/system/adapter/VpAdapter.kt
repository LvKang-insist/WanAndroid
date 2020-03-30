package com.lv.wanandroid.module.system.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.chad.library.adapter.base.BaseViewHolder
import com.lv.wanandroid.R
import com.lv.wanandroid.module.system.RecyclerViewPager2
import com.lv.wanandroid.module.system.bean.TreeBean

/**
 * @name WanAndroid-kotlin
 * @class name：com.lv.wanandroid.module.system.adapter
 * @author 345 QQ:1831712732
 * @time 2020/3/28 20:49
 * @description
 */


class VpAdapter(private val layoutResId: Int, val data: List<TreeBean.Data>) :
    RecyclerView.Adapter<VpAdapter.ViewHolder>() {

    class ViewHolder(val view: View) : BaseViewHolder(view) {
        val mRecycler: RecyclerViewPager2 = view.findViewById(R.id.layout_system_page_rv)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(parent.context).inflate(layoutResId, parent, false))
    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.mRecycler.layoutManager =
            LinearLayoutManager(holder.view.context, LinearLayoutManager.VERTICAL, false)
        val adapter = RvRightAdapterContent(R.layout.system_right_item)
        holder.mRecycler.adapter = adapter
        adapter.setNewData(data[position].children)
    }
}
