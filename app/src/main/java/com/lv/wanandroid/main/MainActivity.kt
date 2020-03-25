package com.lv.wanandroid.main

import android.widget.Toast
import com.google.android.material.tabs.TabLayout
import com.lv.core.utils.TabLayoutMediator
import com.lv.wanandroid.BaseActivity
import com.lv.wanandroid.R
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
        val tabText = arrayOf("首页", "项目", "体系", "干货")
        main_viewpager.adapter = VpAdapter(this)
        TabLayoutMediator(main_tab, main_viewpager,
            object : TabLayoutMediator.OnConfigureTabCallback {
                override fun onConfigureTab(tab: TabLayout.Tab, position: Int) {
                    tab.text = tabText[position]
                }
            }).attach()
    }

    override fun showDialog(result: String) {
        Toast.makeText(this, "弹出 Dialog $result", Toast.LENGTH_LONG).show()
    }




}
