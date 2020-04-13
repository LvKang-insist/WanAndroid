package com.lv.wanandroid.nav.login.loginmvp

import com.lv.core.mvp.BasePresenter
import com.lv.core.mvp.IContract
import com.lv.core.utils.storage.PreferenceUtils
import com.lv.wanandroid.nav.login.bean.LogBean

/**
 * @name WanAndroid-kotlin
 * @class name：com.lv.wanandroid.login.mvp
 * @author 345 QQ:1831712732
 * @time 2020/4/2 20:41
 * @description
 */

class LoginPresenter : BasePresenter<LoginContract.View, LoginModel>(),
    LoginContract.Presenter {
    override fun setModel(): IContract.BaseModel {
        return LoginModel()
    }

    override fun requestLogin(name: String, password: String) {
        val params =
            mutableMapOf<String, Any>(Pair("username", name), Pair("password", password))
        mModel.post("user/login", params) { result ->
            val data = result.format(LogBean::class.java)
            val headers = result.response?.headers
            if (data.errorCode != -1) {
                headers?.forEach {
                    if (it.second.startsWith("token_pass=")) {
                        val start = it.second.indexOf('=')
                        val end = it.second.indexOf(';')
                        val subSequence = it.second.subSequence(start + 1, end)
                        //设置账户信息
                        PreferenceUtils.putAllLogin(
                            "loginUserName=${data.data.username}",
                            "token_pass=$subSequence",
                            data.data.username,
                            data.data.id,
                            true
                        )
                        return@forEach
                    }
                }
                getView()?.resultLogin(true, "登录成功")
            } else {
                getView()?.resultLogin(false, data.errorMsg)
            }
        }
    }


}