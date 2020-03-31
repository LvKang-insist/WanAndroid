package com.lv.wanandroid.module.square.tab

import android.content.Intent
import androidx.recyclerview.widget.LinearLayoutManager
import com.lv.wanandroid.R
import com.lv.wanandroid.base.BaseFragmentLazy
import com.lv.wanandroid.module.square.tab.mvp.SquTabPresenter
import com.lv.wanandroid.module.square.bean.Chapters
import com.lv.wanandroid.module.square.bean.Wxarticle
import com.lv.wanandroid.module.square.tab.adapter.SquTabRvAdapter
import com.lv.wanandroid.module.square.tab.mvp.SquTabContract
import com.lv.wanandroid.web.AgentWebActivity
import kotlinx.android.synthetic.main.frag_tab.*

class SquTabFragment :
    BaseFragmentLazy<SquTabContract.View, SquTabContract.Presenter>, SquTabContract.View {

    private var curPage = 0
    private var pageCount = 0
    lateinit var data: Chapters.Data
    var mAdapterSqu: SquTabRvAdapter? = null

    constructor()
    constructor(data: Chapters.Data) {
        this.data = data
    }

    override fun createPresenter(): SquTabContract.Presenter {
        return SquTabPresenter()
    }

    override fun layoutId(): Int {
        return R.layout.frag_tab
    }

    override fun bindView() {
        if (mAdapterSqu == null || mAdapterSqu!!.data.size <= 0) {
            initRv()
            request(0, data.id)
        }
    }

    private fun initRv() {
        tab_recycler.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        mAdapterSqu = SquTabRvAdapter(R.layout.tab_rv_item)
        tab_recycler.adapter = mAdapterSqu
        tab_refresh.setEnableRefresh(false)
        tab_refresh.setOnLoadMoreListener {
            if (++curPage < pageCount) {
                request(curPage, data.id)
            } else {
                tab_refresh.finishRefreshWithNoMoreData()
            }
        }
        mAdapterSqu?.setOnItemClickListener { adapter, _, position ->
            val data = adapter.data[position] as Wxarticle.Data.Data
            val intent = Intent(context, AgentWebActivity::class.java)
            intent.putExtra("link", data.link)
            context?.startActivity(intent)
        }
    }

    private fun request(curPage: Int, id: Int) {
        mPresenter.requestWx(curPage, id)
    }


    override fun resultWx(curpage: Int, pageCount: Int, data: List<Wxarticle.Data.Data>) {
        this.curPage = curpage
        this.pageCount = pageCount
        if (curPage == 0) {
            mAdapterSqu?.setNewData(data)
        } else {
            mAdapterSqu?.addData(data)
            tab_refresh.finishLoadMore()
        }
    }

    companion object {
        fun start(data: Chapters.Data): SquTabFragment {
            return SquTabFragment(data)
        }
    }


}