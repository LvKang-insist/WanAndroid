package com.lv.wanandroid.nav.login

import android.content.Intent
import com.hjq.toast.ToastUtils
import com.lv.core.basedialog.LoadingView
import com.lv.wanandroid.R
import com.lv.wanandroid.base.BaseActivity
import com.lv.wanandroid.nav.login.loginmvp.LoginContract
import com.lv.wanandroid.nav.login.loginmvp.LoginPresenter
import kotlinx.android.synthetic.main.activity_login.*

/**
 * @name WanAndroid-kotlin
 * @class name：com.lv.wanandroid.login
 * @author 345 QQ:1831712732
 * @time 2020/4/2 20:39
 * @description
 */

class LoginActivity : BaseActivity<LoginContract.View, LoginContract.Presenter>(),
    LoginContract.View {
    override fun createPresenter(): LoginContract.Presenter {
        return LoginPresenter()
    }

    override fun layoutId(): Int {
        return R.layout.activity_login
    }

    override fun bindView() {
        initBar(activity_home_close)
        initLogin()
    }

    private fun initLogin() {
        activity_home_close.setOnClickListener { finish() }
        activity_home_register.setOnClickListener {
            startActivity(Intent(this, RegisterActivity::class.java))
        }
        activity_home_login.setOnClickListener {
            val name = activity_home_name.text.toString()
            val password = activity_home_password.text.toString()
            if (name.isEmpty()) {
                ToastUtils.show("用户名不允许为空")
                return@setOnClickListener
            } else if (password.isEmpty()) {
                ToastUtils.show("密码不允许为空")
                return@setOnClickListener
            }
            LoadingView.showLoading("login", supportFragmentManager)
            mPresenter.requestLogin(name, password)
        }
    }

    override fun resultLogin(isLogin: Boolean, message: String) {
        LoadingView.stopLoading()
        if (isLogin) {
            ToastUtils.show(message)
            finish()
        } else {
            ToastUtils.show(message)
        }
    }


}