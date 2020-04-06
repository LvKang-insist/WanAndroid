package com.lv.wanandroid.main

import android.content.Intent
import android.view.View
import androidx.appcompat.widget.AppCompatTextView
import androidx.appcompat.widget.LinearLayoutCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.FragmentStatePagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT
import com.hjq.toast.ToastUtils
import com.lv.core.utils.storage.PreferenceUtils
import com.lv.wanandroid.R
import com.lv.wanandroid.base.BaseActivity
import com.lv.wanandroid.list.ListActivity
import com.lv.wanandroid.main.adapter.VpAdapter
import com.lv.wanandroid.main.mvp.MainContract
import com.lv.wanandroid.main.mvp.MainPresenter
import com.lv.wanandroid.module.search.SearchActivity
import com.lv.wanandroid.nav.login.LoginActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : BaseActivity<MainContract.View, MainContract.Presenter>(), MainContract.View {

    override fun layoutId(): Int {
        return R.layout.activity_main
    }

    private var mName: AppCompatTextView? = null

    override fun createPresenter(): MainContract.Presenter {
        return MainPresenter()
    }

    override fun bindView() {
        initBar(main_toolbar)
        initTabLayout()
        initDrawer()
        initNav()
        upData()
    }

    private fun initDrawer() {
        main_menu_iv.setOnClickListener {
            if (main_drawerLayout.isDrawerOpen(main_navigation)) {
                main_drawerLayout.closeDrawer(main_navigation)
            } else {
                main_drawerLayout.openDrawer(main_navigation)
            }
        }
        main_drawerLayout.addDrawerListener(object : DrawerLayout.DrawerListener {
            override fun onDrawerStateChanged(newState: Int) {

            }

            override fun onDrawerSlide(drawerView: View, slideOffset: Float) {
                upData()
            }

            override fun onDrawerClosed(drawerView: View) {
            }

            override fun onDrawerOpened(drawerView: View) {
            }
        })
    }

    private fun initNav() {
        val layout: LinearLayoutCompat = main_navigation.getHeaderView(0) as LinearLayoutCompat
        mName = layout.findViewById(R.id.activity_main_nav_name)
        layout.setOnClickListener {
            if (!PreferenceUtils.login) {
                startActivity(Intent(this, LoginActivity::class.java))
            } else {
                ToastUtils.show("已登录")
            }
        }
        main_navigation.setNavigationItemSelectedListener {
            main_drawerLayout.closeDrawer(main_navigation)
            when (it.itemId) {
                R.id.nav_collect -> {
                    val intent = Intent(this, ListActivity::class.java)
                    intent.putExtra(ListActivity.KEY, ListActivity.COLLECT_LIST)
                    intent.putExtra("name", "我的收藏")
                    startActivity(intent)
                }
                R.id.nav_integral -> {
                    ToastUtils.show("我的积分")
                }
                R.id.nav_settings -> {
                    ToastUtils.show("设置")
                }
                R.id.nav_regard -> {
                    ToastUtils.show("关于")
                }
            }
            return@setNavigationItemSelectedListener true
        }
    }

    override fun upData() {

        if (PreferenceUtils.login) {
            mName?.text = PreferenceUtils.userName
        } else {
            mName?.text = "点击进行登录"
        }
    }

    private fun initTabLayout() {

        main_tab.setupWithViewPager(main_viewpager)
        main_viewpager.offscreenPageLimit = 4
        main_viewpager.adapter =
            VpAdapter(supportFragmentManager, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT)

        main_search_iv.setOnClickListener {
            startActivity(Intent(this, SearchActivity::class.java))
        }
        /*   val tabText = arrayOf("首页", "项目", "体系", "干货")
           main_viewpager.adapter = VpAdapter(this)
           TabLayoutMediator(main_tab, main_viewpager,
               object : TabLayoutMediator.OnConfigureTabCallback {
                   override fun onConfigureTab(tab: TabLayout.Tab, position: Int) {
                       tab.text = tabText[position]
                   }
               }).attach()*/
    }

    override fun onBackPressed() {
        if (main_drawerLayout.isDrawerOpen(main_navigation)) {
            main_drawerLayout.closeDrawer(main_navigation)
        } else {
            finish()
        }
    }
}

