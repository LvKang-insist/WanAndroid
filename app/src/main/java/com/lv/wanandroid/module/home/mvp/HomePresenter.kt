package com.lv.wanandroid.module.home.mvp

import com.lv.core.mvp.BasePresenter
import com.lv.core.mvp.IContract
import com.lv.wanandroid.module.home.bean.BannerData

/**
 * @name WanAndroid-kotlin
 * @class name：com.lv.wanandroid.module.home.mvp
 * @author 345 QQ:1831712732
 * @time 2020/3/25 20:39
 * @description
 */

class HomePresenter : BasePresenter<HomeContract.View, HomeModel>(), HomeContract.Presenter {


    override fun setModel(): IContract.BaseModel {
        return HomeModel()
    }

    override fun onDestroy() {
        super.onDestroy()
    }

    override fun requestArticle() {
        mModel.requestArticle { pair ->
            val list1 = pair.first.data
            val list2 = pair.second.data?.datas
            val pageCount = pair.second.data?.pageCount!!
            val curPage = pair.second.data?.curPage!!
            if (list2 != null && list2.size > 0) {
                list1?.let {
                    it.addAll(list2)
                    getView()?.resultArticle(pageCount, curPage, it)
                } ?: getView()?.resultArticle(pageCount, curPage, list2)
            } else {
                if (list1 != null && list1.size > 0) {
                    getView()?.resultArticle(pageCount, curPage, list1)
                }
            }
        }
    }

    override fun requestArticlePage(page: Int) {
        mModel.requestArticlePage(page) {
            val data = it.data!!
            if (data.datas != null && data.datas.size > 0) {
                val pageCount = data.pageCount
                val curPage = data.curPage
                getView()?.resultArticle(pageCount, curPage, data.datas)
            }
        }
    }

    override fun requestBanner() {
        mModel.request("banner/json") {
            getView()?.resultBanner(it.format(BannerData::class.java))
        }
    }
}