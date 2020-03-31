package com.lv.wanandroid.main.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter

import com.lv.wanandroid.module.home.HomeFragment
import com.lv.wanandroid.module.project.ProjectFragment
import com.lv.wanandroid.module.square.SquareFragment
import com.lv.wanandroid.module.system.SystemFragment

//class VpAdapter(fragmentActivity: FragmentActivity) : FragmentStateAdapter(fragmentActivity) {
//    var fragList: List<Fragment> =
//        listOf(HomeFragment(), ProjectFragment(), SystemFragment(), EssenceFragment())
//
//
//    override fun getItemCount(): Int {
//        return fragList.size
//
//    }
//
//    override fun createFragment(position: Int): Fragment {
//        return fragList[position]
//    }
//
//}
class VpAdapter(fragmentManager: FragmentManager, behavior: Int) :
    FragmentStatePagerAdapter(fragmentManager, behavior) {

    val tabText = arrayOf("首页", "项目", "体系", "干货")

    var fragList: List<Fragment> =
        listOf(HomeFragment(), ProjectFragment(), SystemFragment(), SquareFragment())

    override fun getItem(position: Int): Fragment {
        return fragList[position]
    }

    override fun getCount(): Int {
        return fragList.size
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return tabText[position]
    }

}