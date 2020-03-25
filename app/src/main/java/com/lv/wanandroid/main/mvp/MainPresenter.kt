package com.lv.wanandroid.main.mvp

import com.elvishew.xlog.XLog
import com.lv.core.mvp.BasePresenter
import com.lv.core.mvp.IContract

/**
 * @name WanAndroid-kotlin
 * @class name：com.lv.wanandroid.main
 * @author 345 QQ:1831712732
 * @time 2020/3/24 23:20
 * @description
 */
class MainPresenter : BasePresenter<MainContract.View, MainModel>(),
    MainContract.Presenter {
    override fun setModel(): IContract.BaseModel {
        return MainModel()
    }


    override fun request() {
        mModel.request("https://www.baidu.com") {
            getView()?.showDialog(it.value)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
    }


}