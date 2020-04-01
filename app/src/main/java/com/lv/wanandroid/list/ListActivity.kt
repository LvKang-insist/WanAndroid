package com.lv.wanandroid.list

import android.content.Intent
import androidx.recyclerview.widget.LinearLayoutManager
import com.lv.wanandroid.R
import com.lv.wanandroid.base.BaseActivity
import com.lv.wanandroid.list.adapter.ListRvAdapter
import com.lv.wanandroid.list.bean.Article
import com.lv.wanandroid.list.mvp.ListContract
import com.lv.wanandroid.list.mvp.ListPresenter
import kotlinx.android.synthetic.main.activity_list.*

class ListActivity : BaseActivity<ListContract.View, ListContract.Presenter>(), ListContract.View {

    companion object {
        const val KEY = "state"
        /**
         * 体系右侧内容的列表，需要传入 name 和 id
         */
        const val SYSTEM_CONTENT_LIST = 0
        const val SEARCH_LIST = 1
    }

    private var state = -1
    //当前页码
    private var curPage = 0
    //默认页数为 1
    private var pageCount = 1
    private val listRvAdapter = ListRvAdapter(R.layout.activity_list_item)

    private var id = 0
    private var name: String? = null
    override fun createPresenter(): ListContract.Presenter {
        return ListPresenter()
    }

    override fun layoutId(): Int {
        return R.layout.activity_list
    }


    override fun initExtra(intent: Intent) {
        state = intent.getIntExtra(KEY, -1)
        id = intent.getIntExtra("id", 0)
        name = intent.getStringExtra("name")
    }


    override fun bindView() {
        initRv()
        request()
    }

    private fun initRv() {
        activity_list_back.setOnClickListener { finish() }
        activity_list_title.text = name
        activity_list_recycler.layoutManager = LinearLayoutManager(this)
        activity_list_recycler.adapter = listRvAdapter
        activity_list_refresh.setEnableRefresh(false)
        activity_list_refresh.setOnLoadMoreListener {
            request()
        }
    }

    fun request() {
        if (curPage < pageCount) {
            when (state) {
                SYSTEM_CONTENT_LIST -> {
                    mPresenter.requestList(id, curPage)
                }
                SEARCH_LIST -> {
                    mPresenter.postSearch(curPage, name!!)
                }
            }
        } else {
            activity_list_refresh.finishLoadMoreWithNoMoreData()
        }
    }

    override fun resultList(curPage: Int, pageCount: Int, data: List<Article.Data.Data>) {
        this.curPage = curPage
        this.pageCount = pageCount
        if (curPage == 0) {
            listRvAdapter.setNewData(data)
        } else {
            listRvAdapter.addData(data)
            activity_list_refresh.finishLoadMore()
        }
    }
}