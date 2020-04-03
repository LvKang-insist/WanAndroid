package com.lv.wanandroid.nav.login.registermvp

import com.elvishew.xlog.XLog
import com.lv.core.mvp.BasePresenter
import com.lv.core.mvp.IContract
import com.lv.core.utils.storage.PreferenceUtils
import com.lv.wanandroid.nav.login.bean.LogBean
import com.lv.wanandroid.nav.login.registermvp.RegisterContract
import com.lv.wanandroid.nav.login.registermvp.RegisterModel
import retrofit2.http.Body

/**
 * @name WanAndroid-kotlin
 * @class name：com.lv.wanandroid.login.mvp
 * @author 345 QQ:1831712732
 * @time 2020/4/2 20:41
 * @description
 */

class RegisterPresenter : BasePresenter<RegisterContract.View, RegisterModel>(),
    RegisterContract.Presenter {
    override fun setModel(): IContract.BaseModel {
        return RegisterModel()
    }

    override fun requestRegister(name: String, password: String, repassword: String) {
        val params = mutableMapOf<String, Any>(
            Pair("username", name), Pair("password", password),
            Pair("repassword", repassword)
        )
        mModel.post("user/register", params) {
            val data = it.format(LogBean::class.java)
            if (data.errorCode != -1) {
                getView()?.resultRegister(true, "注册成功")
            } else {
                getView()?.resultRegister(false, "注册失败")
            }
        }
    }


}