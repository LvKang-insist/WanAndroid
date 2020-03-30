package com.lv.wanandroid.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.lv.core.mvp.IContract

abstract class BaseFragmentLazy<V : IContract.IBaseView, P : IContract.IBasePresenter<V>> :
    Fragment(), IContract.IBaseView {


    lateinit var mPresenter: P
    private var isViewInitFinished = false

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

    override fun onResume() {
        super.onResume()
        bindView()
    }

    abstract fun createPresenter(): P

    abstract fun layoutId(): Int

    abstract fun bindView()
}