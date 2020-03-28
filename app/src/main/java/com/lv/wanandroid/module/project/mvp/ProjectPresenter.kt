package com.lv.wanandroid.module.project.mvp

import com.lv.core.mvp.BasePresenter
import com.lv.core.mvp.IContract
import com.lv.wanandroid.module.project.bean.Nav
import com.lv.wanandroid.module.system.mvp.SystemModel

/**
 * @name WanAndroid-kotlin
 * @class name：com.lv.wanandroid.module.home.mvp
 * @author 345 QQ:1831712732
 * @time 2020/3/25 20:39
 * @description
 */

class ProjectPresenter : BasePresenter<ProjectContract.View, SystemModel>(),
    ProjectContract.Presenter {

    override fun setModel(): IContract.BaseModel {
        return SystemModel()
    }

    override fun requestNav() {

        mModel.request("project/tree/json") {
            getView()?.resultNav(it.format(Nav::class.java))
        }
    }
}