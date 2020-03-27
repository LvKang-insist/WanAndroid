package com.lv.wanandroid.module.project.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.lv.wanandroid.module.project.bean.Data
import com.lv.wanandroid.module.project.tab.TabFragment

class VpTabAdapter(
    fragmentManager: FragmentManager, private val nav: List<Data>, lifecycle: Lifecycle
) : FragmentStateAdapter(fragmentManager, lifecycle) {
    override fun getItemCount(): Int {
        return nav.size
    }

    override fun createFragment(position: Int): Fragment {
        return TabFragment.start(nav[position])
    }

}