package com.lv.wanandroid.list.mvp

import com.lv.core.mvp.IContract
import com.lv.wanandroid.list.bean.Article

interface ListContract {
    interface View : IContract.IBaseView {
        fun resultList(curPage: Int, pageCount: Int, data: List<Article.Data.Data>)
    }

    interface Presenter : IContract.IBasePresenter<View> {
        fun requestList(id: Int, page: Int)
        fun postSearch(page: Int, search: String)
    }
}

