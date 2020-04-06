package com.lv.wanandroid.module.project

import android.content.Context
import android.view.animation.AccelerateInterpolator
import android.view.animation.DecelerateInterpolator
import android.view.animation.Interpolator
import com.lv.core.basedialog.LoadingView
import com.lv.core.utils.ViewPage2Helpter
import com.lv.core.utils.dip2px
import com.lv.core.view.TabTitleView
import com.lv.wanandroid.R
import com.lv.wanandroid.base.BaseFragmentLazy
import com.lv.wanandroid.main.MainActivity
import com.lv.wanandroid.module.project.adapter.VpTabAdapter
import com.lv.wanandroid.module.project.bean.Nav
import com.lv.wanandroid.module.project.mvp.ProjectContract
import com.lv.wanandroid.module.project.mvp.ProjectPresenter
import kotlinx.android.synthetic.main.frag_project.*
import net.lucode.hackware.magicindicator.buildins.commonnavigator.CommonNavigator
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.CommonNavigatorAdapter
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerIndicator
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerTitleView
import net.lucode.hackware.magicindicator.buildins.commonnavigator.indicators.LinePagerIndicator


@Suppress("DEPRECATION")
class ProjectFragment : BaseFragmentLazy<ProjectContract.View, ProjectContract.Presenter>(),

    ProjectContract.View {

    private var onVisible = false

    override fun createPresenter(): ProjectContract.Presenter {
        return ProjectPresenter()
    }

    override fun layoutId(): Int {
        return R.layout.frag_project
    }

    override fun bindView() {
        if (!onVisible) {
            LoadingView.showLoading("加载中", fragmentManager)
            mPresenter.requestNav()
            onVisible = true
        }
    }


    override fun resultNav(nav: Nav) {
        project_viewpager.offscreenPageLimit = 2
        val commonNavigator = CommonNavigator(context)
        commonNavigator.scrollPivotX = 0.5f //滚动中心点
        commonNavigator.isEnablePivotScroll
        commonNavigator.isEnablePivotScroll = false //中心点滚动
        commonNavigator.adapter = object : CommonNavigatorAdapter() {
            override fun getCount(): Int {
                return nav.data.size
            }

            override fun getTitleView(context: Context, index: Int): IPagerTitleView {
                val titleView = TabTitleView(context)
                titleView.normalColor = resources.getColor(R.color.black)
                titleView.selectedColor = resources.getColor(R.color.red)
                titleView.textSize = 18f
                titleView.text = nav.data[index].name
                titleView.setOnClickListener {
                    project_viewpager.currentItem = index
                }
                return titleView
            }

            override fun getIndicator(context: Context): IPagerIndicator {
                val indicator = LinePagerIndicator(context)
                indicator.lineWidth = dip2px(context, 30f).toFloat()
                indicator.lineHeight = dip2px(context, 4f).toFloat()
                indicator.startInterpolator = AccelerateInterpolator() as Interpolator?
                indicator.endInterpolator = DecelerateInterpolator(1.0f)
                indicator.setColors(resources.getColor(R.color.red))
                indicator.roundRadius = 5f
                indicator.mode = LinePagerIndicator.MODE_EXACTLY
                return indicator
            }
        }
        project_tab_layout.navigator = commonNavigator
        project_viewpager.adapter =
            VpTabAdapter(childFragmentManager, nav.data, (context as MainActivity).lifecycle)
        ViewPage2Helpter.bind(project_tab_layout, project_viewpager)
        LoadingView.stopLoading(500)
    }


}