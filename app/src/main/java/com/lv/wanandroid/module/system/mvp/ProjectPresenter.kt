package com.lv.wanandroid.module.system.mvp

import com.lv.core.mvp.IContract
import com.lv.wanandroid.module.system.bean.Data

/**
 * @name WanAndroid-kotlin
 * @class name：com.lv.wanandroid.system.home.mvp
 * @author 345 QQ:1831712732
 * @time 2020/3/25 20:41
 * @description
 */

interface ProjectPresenter {
    interface View : IContract.IBaseView {
        fun result(data: List<Data>)
    }

    interface Presenter : IContract.IBasePresenter<View> {
        fun requestTree()
    }
}