package com.lv.wanandroid.module.system

import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.lv.wanandroid.R
import com.lv.wanandroid.base.BaseFragmentLazy
import com.lv.wanandroid.module.system.adapter.VpAdapter
import com.lv.wanandroid.module.system.adapter.RvLeftAdapterList
import com.lv.wanandroid.module.system.bean.TreeBean
import com.lv.wanandroid.module.system.mvp.ProjectPresenter
import com.lv.wanandroid.module.system.mvp.SystemPresenter
import kotlinx.android.synthetic.main.frag_system.*


class SystemFragment : BaseFragmentLazy<ProjectPresenter.View, ProjectPresenter.Presenter>(),
    ProjectPresenter.View {
    override fun createPresenter(): ProjectPresenter.Presenter {
        return SystemPresenter()
    }

    override fun layoutId(): Int {
        return R.layout.frag_system
    }

    private val mRvAdapterList by lazy {
        RvLeftAdapterList(R.layout.system_left_item)
    }

    private lateinit var mRvAdapterContent: VpAdapter
    private val mRvContentManager by lazy {
        ViewPagerLayoutManager(context!!, LinearLayoutManager.VERTICAL, false)
    }
    private val mRvListManager by lazy {
        LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
    }


    override
    fun bindView() {
        if (mRvAdapterList.data.size <= 0) {
            initRvList()
        }
    }

    private fun initRvContent(data: List<TreeBean.Data>) {
        mRvAdapterContent = VpAdapter(R.layout.layout_system_page, data)
        frag_system_vp.orientation = ViewPager2.ORIENTATION_VERTICAL
        frag_system_vp.adapter = mRvAdapterContent
    }

    private fun initRvList() {
        mPresenter.requestTree()
        mRvAdapterList.closeLoadAnimation()
        frag_system_rv_list.layoutManager = mRvListManager
        frag_system_rv_list.adapter = mRvAdapterList
        frag_system_rv_list.itemAnimator = null
    }

    override fun result(data: List<TreeBean.Data>) {
        mRvAdapterList.setNewData(data)
        initRvContent(data)
    }

    /**
     * RecyclerView 移动到当前位置，
     *
     * @param manager       设置RecyclerView对应的manager
     * @param mRecyclerView 当前的RecyclerView
     * @param n             要跳转的位置
     */
    private fun moveToPosition(manager: LinearLayoutManager, mRecyclerView: RecyclerView, n: Int) {
        val firstItem = manager.findFirstVisibleItemPosition()
        val lastItem = manager.findLastVisibleItemPosition()
        if (n <= firstItem) {
            mRecyclerView.scrollToPosition(n)
        } else if (n <= lastItem) {
            val top = mRecyclerView.getChildAt(n - firstItem).top
            mRecyclerView.scrollBy(0, top)
        } else {
            mRecyclerView.scrollToPosition(n)
        }
    }
}