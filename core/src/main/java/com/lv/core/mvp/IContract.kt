package com.lv.core.mvp

import android.os.Bundle
import androidx.lifecycle.LifecycleObserver
import com.hjq.toast.ToastUtils
import com.lv.core.utils.storage.PreferenceUtils
import com.www.net.LvHttp
import com.www.net.Result
import org.json.JSONObject

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

        open fun requestCookie(
            url: String, headers: MutableMap<String, String>,
            block: (Boolean, Result) -> Unit
        ) {
            LvHttp.get(url).addHeader(headers).send {
                block(isLogin(it.value), it)
            }
        }

        open fun post(url: String, params: MutableMap<String, Any>, block: (Result) -> Unit) {
            LvHttp.post(url)
                .addParam(params)
                .send { block(it) }
        }

        open fun post(
            url: String, params: MutableMap<String, Any>,
            headers: MutableMap<String, String>, block: (Result) -> Unit
        ) {
            LvHttp.post(url).addParam(params).addHeader(headers).send { block(it) }
        }

        open fun postCookie(
            url: String, params: MutableMap<String, Any>,
            headers: MutableMap<String, String>, block: (Boolean, Result) -> Unit
        ) {
            LvHttp.post(url).addParam(params).addHeader(headers)
                .send {
                    block(isLogin(it.value), it)
                }
        }

        private fun isLogin(value: String): Boolean {
            val json = JSONObject(value)
            val code = json.getInt("errorCode")
            //需要重新登录
            return if (code == -1001) {
                //清除本地账户信息
                PreferenceUtils.putAllLogin("", "", "", -1, false)
                ToastUtils.show(json.getString("errorMsg"))
                false
            } else {
                true
            }
        }
    }

}