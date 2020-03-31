package com.lv.wanandroid.module.system.list.mvp

import com.lv.core.mvp.BasePresenter
import com.lv.core.mvp.IContract
import com.lv.wanandroid.module.system.list.bean.Article

class ListPresenter : BasePresenter<ListContract.View, ListModel>(),
    ListContract.Presenter {

    override fun setModel(): IContract.BaseModel {
        return ListModel()
    }

    override fun requestList(id: Int, page: Int) {
        mModel.request("/article/list/$page/json?cid=$id") {
            val article = it.format(Article::class.java)
            val data = article.data
            getView()?.resultList(data.curPage, data.pageCount, data.datas)
        }
    }
}