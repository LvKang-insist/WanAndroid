package com.lv.wanandroid.module.project.tab.mvp

import com.lv.core.mvp.BasePresenter
import com.lv.core.mvp.IContract
import com.lv.wanandroid.module.project.bean.NavData
import com.lv.wanandroid.module.project.mvp.TabModel

/**
 * @name WanAndroid-kotlin
 * @class name：com.lv.wanandroid.module.home.mvp
 * @author 345 QQ:1831712732
 * @time 2020/3/25 20:39
 * @description
 */

class TabPresenter : BasePresenter<TabContract.View, TabModel>(),
    TabContract.Presenter {

    override fun setModel(): IContract.BaseModel {
        return TabModel()
    }

    override fun requestNavData(curPage: Int, id: Int) {
        mModel.request("project/list/${curPage}/json", mutableMapOf(Pair("cid", id))) {
            val data = it.format(NavData::class.java)
            getView()?.resultNavData(data.data.curPage, data.data.pageCount, data.data.datas)
        }
    }
}