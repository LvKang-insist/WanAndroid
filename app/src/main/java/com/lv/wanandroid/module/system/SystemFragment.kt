package com.lv.wanandroid.module.system

import androidx.recyclerview.widget.LinearLayoutManager
import androidx.viewpager2.widget.ViewPager2
import com.lv.wanandroid.R
import com.lv.wanandroid.base.BaseFragment
import com.lv.wanandroid.module.system.adapter.RvAdapter
import com.lv.wanandroid.module.system.adapter.VpAdapter
import com.lv.wanandroid.module.system.bean.TreeBean
import com.lv.wanandroid.module.system.mvp.ProjectPresenter
import com.lv.wanandroid.module.system.mvp.SystemPresenter
import kotlinx.android.synthetic.main.frag_system.*

class SystemFragment : BaseFragment<ProjectPresenter.View, ProjectPresenter.Presenter>(),
    ProjectPresenter.View {

    override fun createPresenter(): ProjectPresenter.Presenter {
        return SystemPresenter()
    }

    override fun layoutId(): Int {
        return R.layout.frag_system
    }

    private lateinit var mRvAdapter: RvAdapter
    private lateinit var mVpAdapter: VpAdapter

    override
    fun bindView() {
        initRv()

    }

    private fun initVp(data: List<TreeBean.Data>) {
        mVpAdapter = VpAdapter(data)
        frag_system_vp.adapter = mVpAdapter
        frag_system_vp.orientation = ViewPager2.ORIENTATION_VERTICAL
    }

    private fun initRv() {
        mPresenter.requestTree()
        frag_system_rv.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        mRvAdapter = RvAdapter(R.layout.system_lv_item)
        frag_system_rv.adapter = mRvAdapter
        frag_system_rv.itemAnimator = null
        mRvAdapter.closeLoadAnimation()
    }

    override fun result(data: List<TreeBean.Data>) {
        mRvAdapter.setNewData(data)
        initVp(data)
    }
}