package com.lv.wanandroid.module.square.mvp

import com.lv.core.mvp.IContract
import com.lv.wanandroid.module.square.bean.Chapters

interface SquareContract {
    interface View : IContract.IBaseView {
        fun resultWx(data:List<Chapters.Data>)
    }

    interface Presenter : IContract.IBasePresenter<View> {
        fun requestWx()
    }
}