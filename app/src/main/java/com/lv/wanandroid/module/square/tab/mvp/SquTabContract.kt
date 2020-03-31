package com.lv.wanandroid.module.square.tab.mvp

import com.lv.core.mvp.IContract
import com.lv.wanandroid.module.project.bean.DataX
import com.lv.wanandroid.module.square.bean.Wxarticle

/**
 * @name WanAndroid-kotlin
 * @class name：com.lv.wanandroid.module.home.mvp
 * @author 345 QQ:1831712732
 * @time 2020/3/25 20:41
 * @description
 */

interface SquTabContract {
    interface View : IContract.IBaseView {
        fun resultWx(curpage: Int, pageCount: Int, data: List<Wxarticle.Data.Data>)
    }

    interface Presenter : IContract.IBasePresenter<View> {
        fun requestWx(curpage: Int, id: Int)
    }
}