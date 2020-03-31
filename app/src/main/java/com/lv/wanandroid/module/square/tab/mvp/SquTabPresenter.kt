package com.lv.wanandroid.module.square.tab.mvp

import com.lv.core.mvp.BasePresenter
import com.lv.core.mvp.IContract
import com.lv.wanandroid.module.square.bean.Wxarticle


/**
 * @name WanAndroid-kotlin
 * @class name：com.lv.wanandroid.module.square.tab.mvp
 * @author 345 QQ:1831712732
 * @time 2020/3/25 20:39
 * @description
 */

@Suppress("NAME_SHADOWING")
class SquTabPresenter : BasePresenter<SquTabContract.View, SquTabModel>(),
    SquTabContract.Presenter {

    override fun setModel(): IContract.BaseModel {
        return SquTabModel()
    }

    override fun requestWx(curpage: Int, id: Int) {
        mModel.request("wxarticle/list/$id/$curpage/json") {
            val wxarticle = it.format(Wxarticle::class.java)
            val curpage = wxarticle.data.curPage
            val pageCount = wxarticle.data.pageCount
            getView()?.resultWx(curpage, pageCount, wxarticle.data.datas)
        }
    }

}