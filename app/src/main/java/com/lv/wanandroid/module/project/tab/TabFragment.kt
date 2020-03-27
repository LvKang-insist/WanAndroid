package com.lv.wanandroid.module.project.tab

import android.content.Intent
import androidx.recyclerview.widget.LinearLayoutManager
import com.lv.wanandroid.R
import com.lv.wanandroid.base.BaseFragment
import com.lv.wanandroid.module.project.bean.Data
import com.lv.wanandroid.module.project.bean.DataX
import com.lv.wanandroid.module.project.tab.adapter.TabRvAdapter
import com.lv.wanandroid.module.project.tab.mvp.TabContract
import com.lv.wanandroid.module.project.tab.mvp.TabPresenter
import com.lv.wanandroid.web.WebActivity
import kotlinx.android.synthetic.main.frag_tab.*

class TabFragment :
    BaseFragment<TabContract.View, TabContract.Presenter>, TabContract.View {

    private var curPage = 0
    private var pageCount = 0
    lateinit var nav: Data
    lateinit var mAdapter: TabRvAdapter

    constructor()
    constructor(nav: Data) {
        this.nav = nav
    }

    override fun createPresenter(): TabContract.Presenter {
        return TabPresenter()
    }

    override fun layoutId(): Int {
        return R.layout.frag_tab
    }

    override fun bindView() {
        initRv()
        request(0)
    }

    private fun initRv() {
        tab_recycler.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        mAdapter = TabRvAdapter(R.layout.tab_rv_item)
        tab_recycler.adapter = mAdapter
        tab_refresh.setEnableRefresh(false)
        tab_refresh.setOnLoadMoreListener {
            if (++curPage < pageCount) {
                request(curPage)
            } else {
                tab_refresh.finishRefreshWithNoMoreData()
            }
        }
        mAdapter.setOnItemClickListener { adapter, view, position ->
            val data = adapter.data[position] as DataX
            val intent = Intent(context, WebActivity::class.java)
            intent.putExtra("link", data.link)
            context?.startActivity(intent)
        }
    }

    private fun request(curPage: Int) {
        mPresenter.requestNavData(curPage, nav.id)
    }

    override fun resultNavData(curPage: Int, pageCount: Int, datas: List<DataX>) {
        this.curPage = curPage
        this.pageCount = pageCount
        if (curPage == 0) {
            mAdapter.setNewData(datas)
        } else {
            mAdapter.addData(datas)
            tab_refresh.finishLoadMore()
        }
    }

    companion object {
        fun start(nav: Data): TabFragment {
            return TabFragment(nav)
        }
    }
}