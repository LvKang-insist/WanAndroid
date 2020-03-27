package com.lv.wanandroid.module.project.tab.mvp

import com.lv.core.mvp.IContract
import com.lv.wanandroid.module.project.bean.DataX

/**
 * @name WanAndroid-kotlin
 * @class name：com.lv.wanandroid.module.home.mvp
 * @author 345 QQ:1831712732
 * @time 2020/3/25 20:41
 * @description
 */

interface TabContract {
    interface View : IContract.IBaseView {
        fun resultNavData(
            curPage: Int,
            pageCount: Int,
            datas: List<DataX>
        )
    }

    interface Presenter : IContract.IBasePresenter<View> {
        fun requestNavData(curPage: Int, id: Int)
    }
}