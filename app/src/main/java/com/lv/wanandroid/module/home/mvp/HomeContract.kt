package com.lv.wanandroid.module.home.mvp

import com.lv.core.mvp.IContract
import com.lv.wanandroid.module.home.bean.Article
import com.lv.wanandroid.module.home.bean.BannerData

/**
 * @name WanAndroid-kotlin
 * @class name：com.lv.wanandroid.module.home.mvp
 * @author 345 QQ:1831712732
 * @time 2020/3/25 20:41
 * @description
 */

interface HomeContract {
    interface View : IContract.IBaseView {
        /**
         * @param pageCount 总页数
         * @param curPage 当前页
         * 当前页必须小于总页数
         */
        fun resultArticle(pageCount: Int, curPage: Int, mutableList: MutableList<Article?>)

        fun resultBanner(bannerData: BannerData)
    }

    interface Presenter : IContract.IBasePresenter<View> {
        fun requestArticle()
        fun requestArticlePage(page: Int)
        fun requestBanner()
    }
}