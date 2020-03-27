package com.lv.wanandroid.main

import android.widget.Toast
import androidx.fragment.app.FragmentStatePagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT
import com.lv.wanandroid.R
import com.lv.wanandroid.base.BaseActivity
import com.lv.wanandroid.main.adapter.VpAdapter
import com.lv.wanandroid.main.mvp.MainContract
import com.lv.wanandroid.main.mvp.MainPresenter
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : BaseActivity<MainContract.View, MainContract.Presenter>(), MainContract.View {

    override fun layoutId(): Int {
        return R.layout.activity_main
    }

    override fun createPresenter(): MainContract.Presenter {
        return MainPresenter()
    }

    override fun bindView() {
        initTabLayout()
    }

    private fun initTabLayout() {
//        val tabText = arrayOf("首页", "项目", "体系", "干货")
//        TabLayoutMediator(main_tab, main_viewpager,
//            object : TabLayoutMediator.OnConfigureTabCallback {
//                override fun onConfigureTab(tab: TabLayout.Tab, position: Int) {
//                    tab.text = tabText[position]
//                }
//            }).attach()
        main_tab.setupWithViewPager(main_viewpager)
        main_viewpager.adapter = VpAdapter(supportFragmentManager, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT)
        main_viewpager.offscreenPageLimit = 3
    }

    override fun showDialog(result: String) {
        Toast.makeText(this, "弹出 Dialog $result", Toast.LENGTH_LONG).show()
    }


}
