package com.lv.wanandroid.main.mvp

import com.lv.core.mvp.IContract

/**
 * @name WanAndroid-kotlin
 * @class name：com.lv.wanandroid.main
 * @author 345 QQ:1831712732
 * @time 2020/3/24 23:16
 * @description
 */

interface MainContract {
    interface View : IContract.IBaseView {
        fun showDialog(result:String)

    }

    interface Presenter : IContract.IBasePresenter<View> {
        fun  request()
    }
}