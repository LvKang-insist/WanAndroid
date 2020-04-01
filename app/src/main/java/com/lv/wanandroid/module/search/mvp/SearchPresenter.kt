package com.lv.wanandroid.module.search.mvp

import com.lv.core.mvp.BasePresenter
import com.lv.core.mvp.IContract
import com.lv.wanandroid.module.search.bean.HotKey

class SearchPresenter : BasePresenter<SearchContract.View, SearchModel>(),
    SearchContract.Presenter {

    override fun setModel(): IContract.BaseModel {
        return SearchModel()
    }

    override fun requestHotkey() {
        mModel.request("hotkey/json") {
            getView()?.resultHotKey(it.format(HotKey::class.java).data)
        }
    }

}