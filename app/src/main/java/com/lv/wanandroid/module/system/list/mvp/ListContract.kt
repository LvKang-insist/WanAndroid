package com.lv.wanandroid.module.system.list.mvp

import com.lv.core.mvp.IContract
import com.lv.wanandroid.module.system.list.bean.Article

interface ListContract {
    interface View : IContract.IBaseView {
        fun resultList(curPage: Int, pageCount: Int, data: List<Article.Data.Data>)
    }

    interface Presenter : IContract.IBasePresenter<View> {
        fun requestList(id: Int, page: Int)
    }
}

