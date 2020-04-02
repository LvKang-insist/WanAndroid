package com.lv.wanandroid.base

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.lv.core.mvp.IContract
import com.umeng.analytics.MobclickAgent

/**
 * @name WanAndroid-kotlin
 * @class name：com.lv.wanandroid.main
 * @author 345 QQ:1831712732
 * @time 2020/3/24 22:59
 * @description
 */

@Suppress("UNCHECKED_CAST")
abstract class BaseActivity<V : IContract.IBaseView, P : IContract.IBasePresenter<V>> :
    AppCompatActivity(), IContract.IBaseView {

    lateinit var mPresenter: P
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(layoutId())
        mPresenter = createPresenter()
        mPresenter.attachView(this as V, savedInstanceState)
        //P 层感知生命周期
        lifecycle.addObserver(mPresenter)
        initExtra(intent)
        bindView()
    }

    override fun onResume() {
        super.onResume()
        //友盟统计
        MobclickAgent.onResume(this)
    }

    override fun onPause() {
        super.onPause()
        MobclickAgent.onPause(this)
    }

    open fun initExtra(intent: Intent) {}

    abstract fun createPresenter(): P

    abstract fun layoutId(): Int

    abstract fun bindView()

}