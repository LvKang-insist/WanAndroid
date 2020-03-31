package com.lv.wanandroid.module.square.mvp

import com.lv.core.mvp.BasePresenter
import com.lv.core.mvp.IContract
import com.lv.wanandroid.module.square.bean.Chapters

class SquarePresenter : BasePresenter<SquareContract.View, SquareModel>(),
    SquareContract.Presenter {

    override fun setModel(): IContract.BaseModel {
        return SquareModel()
    }

    override fun requestWx() {
        mModel.request("wxarticle/chapters/json") {
            getView()?.resultWx(it.format(Chapters::class.java).data)
        }
    }
}