package com.lv.wanandroid.web.mvp

import com.lv.core.mvp.IContract

/**
 * @name WanAndroid-kotlin
 * @class name：com.lv.wanandroid.main
 * @author 345 QQ:1831712732
 * @time 2020/3/24 23:16
 * @description
 */

interface WebContract {
    interface View : IContract.IBaseView {

    }

    interface Presenter : IContract.IBasePresenter<View> {
    }
}