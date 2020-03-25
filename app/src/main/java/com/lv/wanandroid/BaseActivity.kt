package com.lv.wanandroid

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.lv.core.mvp.IContract

/**
 * @name WanAndroid-kotlin
 * @class name：com.lv.wanandroid.main
 * @author 345 QQ:1831712732
 * @time 2020/3/24 22:59
 * @description
 */

@Suppress("UNCHECKED_CAST")
abstract class BaseActivity<V : IContract.IBaseView, P : IContract.IBasePresenter<V>> :
    AppCompatActivity(),
    IContract.IBaseView {

    lateinit var mPresenter: P
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(layoutId())
        mPresenter = createPresenter()
        mPresenter.attachView(this as V, savedInstanceState)
        //P 层感知生命周期
        lifecycle.addObserver(mPresenter)
        bindView()
    }

    abstract fun createPresenter(): P

    abstract fun layoutId(): Int

    abstract fun bindView()

}