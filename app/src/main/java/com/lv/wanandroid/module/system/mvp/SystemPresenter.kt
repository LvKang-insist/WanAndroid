package com.lv.wanandroid.module.system.mvp

import com.lv.core.mvp.BasePresenter
import com.lv.core.mvp.IContract
import com.lv.wanandroid.module.system.bean.TreeBean

/**
 * @name WanAndroid-kotlin
 * @class name：com.lv.wanandroid.module.system.mvp
 * @author 345 QQ:1831712732
 * @time 2020/3/25 20:39
 * @description
 */

class SystemPresenter : BasePresenter<ProjectPresenter.View, SystemModel>(),
    ProjectPresenter.Presenter {

    override fun setModel(): IContract.BaseModel {
        return SystemModel()
    }

    override fun requestTree() {
        mModel.request("tree/json") {
            getView()?.result(it.format(TreeBean::class.java).data)
        }
    }

}