package com.lv.wanandroid.module.square.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.lv.wanandroid.module.square.bean.Chapters
import com.lv.wanandroid.module.square.tab.SquTabFragment

class SquVpTabAdapter(
    fragmentManager: FragmentManager, private val data: List<Chapters.Data>, lifecycle: Lifecycle
) : FragmentStateAdapter(fragmentManager, lifecycle) {
    override fun getItemCount(): Int {
        return data.size
    }

    override fun createFragment(position: Int): Fragment {
        return SquTabFragment.start(data[position])
    }

}