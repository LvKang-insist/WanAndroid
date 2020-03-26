package com.lv.wanandroid.web.mvp

import com.lv.core.mvp.BasePresenter
import com.lv.core.mvp.IContract

class WebPresenter :BasePresenter<WebContract.View,WebModel>(),WebContract.Presenter{
    override fun setModel(): IContract.BaseModel {
        return WebModel()
    }
}