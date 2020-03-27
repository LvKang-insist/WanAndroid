package com.lv.wanandroid.module.project.mvp

import com.elvishew.xlog.XLog
import com.lv.core.mvp.BasePresenter
import com.lv.core.mvp.IContract
import com.lv.wanandroid.module.project.bean.Nav

/**
 * @name WanAndroid-kotlin
 * @class name：com.lv.wanandroid.module.home.mvp
 * @author 345 QQ:1831712732
 * @time 2020/3/25 20:39
 * @description
 */

class ProjectPresenter : BasePresenter<ProjectContract.View, ProjectModel>(),
    ProjectContract.Presenter {

    override fun setModel(): IContract.BaseModel {
        return ProjectModel()
    }

    override fun requestNav() {

        mModel.request("project/tree/json") {
            getView()?.resultNav(it.format(Nav::class.java))
        }
    }
}

class Ex(s: String) : Exception(s) {
    init {
        XLog.e("异常退出")
    }
}