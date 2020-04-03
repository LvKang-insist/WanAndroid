package com.lv.wanandroid.nav.login.registermvp

import com.lv.core.mvp.IContract

/**
 * @name WanAndroid-kotlin
 * @class name：com.lv.wanandroid.login.registermvp
 * @author 345 QQ:1831712732
 * @time 2020/4/2 21:42
 * @description
 */

interface RegisterContract {
    interface View : IContract.IBaseView {
        fun resultRegister(isRegister: Boolean, message: String)
    }

    interface Presenter : IContract.IBasePresenter<View> {
        fun requestRegister(name: String, password: String, repassword: String)
    }
}