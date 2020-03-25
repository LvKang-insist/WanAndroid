package com.lv.wanandroid.main.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.lv.wanandroid.module.essence.EssenceFragment
import com.lv.wanandroid.module.home.HomeFragment
import com.lv.wanandroid.module.project.ProjectFragment
import com.lv.wanandroid.module.system.SystemFragment

class VpAdapter(fragmentActivity: FragmentActivity) : FragmentStateAdapter(fragmentActivity) {
    var fragList: List<Fragment> =
        listOf(HomeFragment(), ProjectFragment(), SystemFragment(), EssenceFragment())


    override fun getItemCount(): Int {
        return fragList.size

    }

    override fun createFragment(position: Int): Fragment {
        return fragList[position]
    }

}