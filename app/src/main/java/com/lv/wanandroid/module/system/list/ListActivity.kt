package com.lv.wanandroid.module.system.list

import android.content.Intent
import androidx.recyclerview.widget.LinearLayoutManager
import com.lv.wanandroid.R
import com.lv.wanandroid.base.BaseActivity
import com.lv.wanandroid.module.system.list.adapter.ListRvAdapter
import com.lv.wanandroid.module.system.list.bean.Article
import com.lv.wanandroid.module.system.list.mvp.ListContract
import com.lv.wanandroid.module.system.list.mvp.ListPresenter
import kotlinx.android.synthetic.main.activity_list.*

class ListActivity : BaseActivity<ListContract.View, ListContract.Presenter>(), ListContract.View {

    private var curPage = 0
    private var pageCount = 0
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
        id = intent.getIntExtra("id", 0)
        name = intent.getStringExtra("name")
    }


    override fun bindView() {
        activity_list_back.setOnClickListener { finish() }
        activity_list_title.text = name
        activity_list_recycler.layoutManager = LinearLayoutManager(this)
        activity_list_recycler.adapter = listRvAdapter
        mPresenter.requestList(id, 0)
        activity_list_refresh.setEnableRefresh(false)
        activity_list_refresh.setOnLoadMoreListener {
            if (++curPage<pageCount){
                mPresenter.requestList(id, curPage)
            }else{
                activity_list_refresh.finishLoadMoreWithNoMoreData()
            }
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