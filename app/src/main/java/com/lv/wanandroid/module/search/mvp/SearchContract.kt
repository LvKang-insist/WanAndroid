package com.lv.wanandroid.module.search.mvp

import com.lv.core.mvp.IContract
import com.lv.wanandroid.module.search.bean.HotKey

class SearchContract {
    interface View : IContract.IBaseView {
        fun resultHotKey(hotKey: List<HotKey.Data>)
    }

    interface Presenter : IContract.IBasePresenter<View> {
        fun requestHotkey()
    }
}