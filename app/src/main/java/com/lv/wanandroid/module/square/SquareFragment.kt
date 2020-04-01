package com.lv.wanandroid.module.square

import android.content.Context
import android.view.animation.AccelerateInterpolator
import android.view.animation.DecelerateInterpolator
import com.lv.core.basedialog.LoadingView
import com.lv.core.utils.ViewPage2Helpter
import com.lv.core.utils.dip2px
import com.lv.core.view.TabTitleView
import com.lv.wanandroid.R
import com.lv.wanandroid.base.BaseFragmentLazy
import com.lv.wanandroid.main.MainActivity
import com.lv.wanandroid.module.square.adapter.SquVpTabAdapter
import com.lv.wanandroid.module.square.bean.Chapters
import com.lv.wanandroid.module.square.mvp.SquareContract
import com.lv.wanandroid.module.square.mvp.SquarePresenter
import kotlinx.android.synthetic.main.frag_square.*
import net.lucode.hackware.magicindicator.buildins.commonnavigator.CommonNavigator
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.CommonNavigatorAdapter
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerIndicator
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerTitleView
import net.lucode.hackware.magicindicator.buildins.commonnavigator.indicators.LinePagerIndicator

class SquareFragment : BaseFragmentLazy<SquareContract.View, SquareContract.Presenter>(),
    SquareContract.View {

    override fun createPresenter(): SquareContract.Presenter {
        return SquarePresenter()
    }

    override fun layoutId(): Int {
        return R.layout.frag_square
    }

    var isLoading: Boolean = false

    override fun bindView() {
        if (!isLoading) {
            LoadingView.showLoading("加载中", fragmentManager)
            mPresenter.requestWx()
        }
    }

    override fun resultWx(data: List<Chapters.Data>) {
        val commonNavigator = CommonNavigator(context)
        commonNavigator.scrollPivotX = 0.5f //滚动中心点
        commonNavigator.isEnablePivotScroll
        commonNavigator.isEnablePivotScroll = false //中心点滚动
        commonNavigator.adapter = object : CommonNavigatorAdapter() {
            override fun getCount(): Int {
                return data.size
            }

            override fun getTitleView(context: Context, index: Int): IPagerTitleView {
                val titleView = TabTitleView(context)
                titleView.normalColor = resources.getColor(R.color.black)
                titleView.selectedColor = resources.getColor(R.color.red)
                titleView.textSize = 18f
                titleView.text = data[index].name
                titleView.setOnClickListener {
                    frag_square_vp.currentItem = index
                }
                return titleView
            }

            override fun getIndicator(context: Context): IPagerIndicator {
                val indicator = LinePagerIndicator(context)
                indicator.lineWidth = dip2px(context, 30f).toFloat()
                indicator.lineHeight = dip2px(context, 4f).toFloat()
                indicator.startInterpolator = AccelerateInterpolator()
                indicator.endInterpolator = DecelerateInterpolator(1.0f)
                indicator.setColors(resources.getColor(R.color.red))
                indicator.roundRadius = 5f
                indicator.mode = LinePagerIndicator.MODE_EXACTLY
                return indicator
            }
        }
        frag_square_tabLayout.navigator = commonNavigator
        frag_square_vp.adapter =
            SquVpTabAdapter(childFragmentManager, data, (context as MainActivity).lifecycle)
        ViewPage2Helpter.bind(frag_square_tabLayout, frag_square_vp)
        LoadingView.stopLoading(500)
        isLoading = true
    }

}