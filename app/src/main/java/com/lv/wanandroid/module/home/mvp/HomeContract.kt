package com.lv.wanandroid.module.home.mvp

import com.lv.core.mvp.IContract

/**
 * @name WanAndroid-kotlin
 * @class name：com.lv.wanandroid.module.home.mvp
 * @author 345 QQ:1831712732
 * @time 2020/3/25 20:41
 * @description
 */

interface HomeContract {
    interface View : IContract.IBaseView {

    }

    interface Presenter : IContract.IBasePresenter<View> {
        fun requestArticle()
        fun requestArticlePage()
    }
}