package com.lv.core.mvp

import android.os.Bundle
import androidx.lifecycle.LifecycleObserver
import com.www.net.LvHttp
import com.www.net.Result

/**
 *
 */
interface IContract {

    /**
     * V 层接口
     */
    interface IBaseView {

    }

    /**
     *  P 层接口
     */
    interface IBasePresenter<V : IBaseView> : LifecycleObserver {
        fun attachView(view: V, savedInstanceState: Bundle?)

        fun getView(): V?

    }

    /**
     * 数据层 -> 数据库操作，网络操作，文件操作等等...
     */
    abstract class BaseModel {
        open fun request(url: String, params: MutableMap<String, Any>, block: (Result) -> Unit) {
            LvHttp.get(url)
                .addParam(params)
                .send {
                    block(it)
                }
        }

        open fun request(url: String, block: (Result) -> Unit) {
            LvHttp.get(url)
                .send {
                    block(it)
                }
        }

        open fun post(url: String, params: MutableMap<String, Any>, block: (Result) -> Unit) {
            LvHttp.post(url)
                .addParam(params)
                .send { block(it) }
        }
    }

}