package com.lv.wanandroid.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.lv.core.mvp.IContract
import com.lv.wanandroid.R

/**
 * @name WanAndroid-kotlin
 * @class name：com.lv.wanandroid.base
 * @author 345 QQ:1831712732
 * @time 2020/3/25 20:34
 * @description
 */

@Suppress("UNCHECKED_CAST")
abstract class BaseFragment<V : IContract.IBaseView, P : IContract.IBasePresenter<V>> :
    Fragment(), IContract.IBaseView {

    lateinit var mPresenter: P

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mPresenter = createPresenter()
        mPresenter.attachView(this as V, savedInstanceState)
        //P 层感知生命周期
        lifecycle.addObserver(mPresenter)

        return LayoutInflater.from(context).inflate(layoutId(), container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        bindView()
    }

    abstract fun createPresenter(): P

    abstract fun layoutId(): Int

    abstract fun bindView()

}