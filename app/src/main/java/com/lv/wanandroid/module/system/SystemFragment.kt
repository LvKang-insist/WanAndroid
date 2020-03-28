package com.lv.wanandroid.module.system

import androidx.recyclerview.widget.LinearLayoutManager
import com.lv.wanandroid.R
import com.lv.wanandroid.base.BaseFragment
import com.lv.wanandroid.module.system.adapter.SyStemLvAdapter
import com.lv.wanandroid.module.system.bean.Data
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

    lateinit var mAdapter: SyStemLvAdapter

    override
    fun bindView() {
        mPresenter.requestTree()
        frag_system_rv.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        mAdapter = SyStemLvAdapter(R.layout.system_lv_item)
        frag_system_rv.adapter = mAdapter
        frag_system_rv.itemAnimator = null
        mAdapter.closeLoadAnimation()
    }

    override fun result(data: List<Data>) {
        mAdapter.setNewData(data)
    }
}