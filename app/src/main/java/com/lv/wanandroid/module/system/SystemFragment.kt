package com.lv.wanandroid.module.system

import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.LinearSmoothScroller
import androidx.viewpager2.widget.ViewPager2
import com.lv.wanandroid.R
import com.lv.wanandroid.base.BaseFragmentLazy
import com.lv.wanandroid.module.system.adapter.RvLeftAdapterLeft
import com.lv.wanandroid.module.system.adapter.VpAdapter
import com.lv.wanandroid.module.system.bean.TreeBean
import com.lv.wanandroid.module.system.mvp.HomeContract
import com.lv.wanandroid.module.system.mvp.SystemPresenter
import kotlinx.android.synthetic.main.frag_system.*

/**
 * 体系
 */
class SystemFragment : BaseFragmentLazy<HomeContract.View, HomeContract.Presenter>(),
    HomeContract.View {
    override fun createPresenter(): HomeContract.Presenter {
        return SystemPresenter()
    }

    override fun layoutId(): Int {
        return R.layout.frag_system
    }

    private val mRvAdapterLeft = RvLeftAdapterLeft(R.layout.system_left_item)
    private val layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)

    override
    fun bindView() {
        if (mRvAdapterLeft.data.size <= 0) {
            initRvList()
        }
    }

    private fun initVpContent(data: List<TreeBean.Data>) {
        val mViewPager = VpAdapter(R.layout.layout_system_page, data)
        frag_system_vp.orientation = ViewPager2.ORIENTATION_VERTICAL
        frag_system_vp.adapter = mViewPager
        frag_system_vp.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageScrolled(
                position: Int, positionOffset: Float, positionOffsetPixels: Int
            ) {
                moveTo(position)
                mRvAdapterLeft.setPos(position)
            }
        })
    }

    /**
     * 移动到指定的位置
     */
    fun moveTo(position: Int) {
        val smoothScroller = object : LinearSmoothScroller(context) {
            override fun getVerticalSnapPreference(): Int {
                return SNAP_TO_ANY;
            }
        }
        smoothScroller.targetPosition = position
        layoutManager.startSmoothScroll(smoothScroller)
    }


    private fun initRvList() {
        mPresenter.requestTree()
        mRvAdapterLeft.closeLoadAnimation()
        frag_system_rv_left.layoutManager =
            layoutManager
        frag_system_rv_left.adapter = mRvAdapterLeft
        frag_system_rv_left.itemAnimator = null
        //与Vp 联动
        mRvAdapterLeft.listener = {
            frag_system_vp.setCurrentItem(it, false)
        }
    }

    override fun result(data: List<TreeBean.Data>) {
        mRvAdapterLeft.setNewData(data)
        initVpContent(data)
    }
}