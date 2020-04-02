package com.lv.wanandroid.login

import com.hjq.toast.ToastUtils
import com.lv.wanandroid.R
import com.lv.wanandroid.base.BaseActivity
import com.lv.wanandroid.login.loginmvp.LoginContract
import com.lv.wanandroid.login.registermvp.RegisterContract
import com.lv.wanandroid.login.registermvp.RegisterPresenter
import kotlinx.android.synthetic.main.activity_register.*

/**
 * @name WanAndroid-kotlin
 * @class name：com.lv.wanandroid.login
 * @author 345 QQ:1831712732
 * @time 2020/4/2 21:39
 * @description
 */

class RegisterActivity : BaseActivity<RegisterContract.View, RegisterContract.Presenter>(),
    RegisterContract.View {

    override fun createPresenter(): RegisterContract.Presenter {
        return RegisterPresenter()
    }

    override fun layoutId(): Int {
        return R.layout.activity_register
    }

    override fun bindView() {
        initRegister()
    }

    private fun initRegister() {
        activity_register.setOnClickListener {
            val name = activity_register_username.text.toString()
            val password = activity_register_password.text.toString()
            val repassword = activity_register_repassword.text.toString()
            when {
                name.isEmpty() -> {
                    ToastUtils.show("请输入用户名")
                }
                password.isEmpty() -> {
                    ToastUtils.show("请输入密码")
                }
                repassword.isEmpty() -> {
                    ToastUtils.show("请确认密码")
                }
                password != repassword -> {
                    activity_register_password.setText("")
                    activity_register_repassword.setText("")
                    ToastUtils.show("密码不一致，请重新输入")
                }
                else -> {
                    mPresenter.requestRegister(name, password, repassword)
                }
            }
        }
    }

    override fun resultRegister(isRegister: Boolean, message: String) {
        if (isRegister) {
            ToastUtils.show(message)
            finish()
        } else {
            ToastUtils.show(message)
        }
    }

}