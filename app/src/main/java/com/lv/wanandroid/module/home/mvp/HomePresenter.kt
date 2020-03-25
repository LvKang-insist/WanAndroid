package com.lv.wanandroid.module.home.mvp

import com.elvishew.xlog.XLog
import com.lv.core.mvp.BasePresenter
import com.lv.core.mvp.IContract

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
        mModel.requestArticle {
            it.first.data?.forEach { article ->
                XLog.e(article?.title)
            }
            XLog.e("-----------------")
            it.second.data?.datas?.forEach { article ->
                XLog.e(article?.title)
            }
        }
    }

    override fun requestArticlePage() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }


}