package com.lv.core.mvp

import android.os.Bundle
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.OnLifecycleEvent
import com.lv.core.mvp.IContract.BaseModel
import java.lang.ref.WeakReference

/**
 * @name WanAndroid-kotlin
 * @class name：com.lv.core.mvp
 * @author 345 QQ:1831712732
 * @time 2020/3/24 22:40
 * @description 专门负责交互 -> 将 UI 和 数据层进行关联
 */
@Suppress("UNCHECKED_CAST")
abstract class BasePresenter<V : IContract.IBaseView, M : BaseModel> :
    IContract.IBasePresenter<V> {
    /**
     * View
     */
    private var viewRef: WeakReference<V>? = null

    /**
     * Model
     */
    abstract fun setModel(): BaseModel


    val mModel by lazy {
        setModel() as M
    }


    override fun getView(): V? {
        return viewRef?.get()
    }

    override fun attachView(view: V, savedInstanceState: Bundle?) {
        viewRef = WeakReference<V>(view)
    }


    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    fun onDestroy() {
        viewRef?.clear()
        viewRef = null
    }

}