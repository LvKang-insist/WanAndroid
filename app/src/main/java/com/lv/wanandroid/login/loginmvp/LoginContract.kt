package com.lv.wanandroid.login.loginmvp

import com.lv.core.mvp.IContract
import com.lv.wanandroid.login.bean.LogBean

/**
 * @name WanAndroid-kotlin
 * @class name：com.lv.wanandroid.login.mvp
 * @author 345 QQ:1831712732
 * @time 2020/4/2 20:41
 * @description
 */
interface LoginContract {
    interface View : IContract.IBaseView {
        fun resultLogin(isLogin: Boolean,message:String)
    }

    interface Presenter : IContract.IBasePresenter<View> {
        fun requestLogin(name: String, password: String)
    }
}