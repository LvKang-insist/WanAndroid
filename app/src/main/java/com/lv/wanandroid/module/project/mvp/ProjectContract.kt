package com.lv.wanandroid.module.project.mvp

import com.lv.core.mvp.IContract
import com.lv.wanandroid.module.project.bean.Nav

/**
 * @name WanAndroid-kotlin
 * @class name：com.lv.wanandroid.module.home.mvp
 * @author 345 QQ:1831712732
 * @time 2020/3/25 20:41
 * @description
 */

interface ProjectContract {
    interface View : IContract.IBaseView {
        fun resultNav(nav: Nav)
    }

    interface Presenter : IContract.IBasePresenter<View> {
        /**
         * 导航数据
         */
        fun requestNav()
    }
}