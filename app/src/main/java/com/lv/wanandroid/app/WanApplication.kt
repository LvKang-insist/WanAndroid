package com.lv.wanandroid.app

import android.app.Application
import android.util.Log
import com.elvishew.xlog.LogConfiguration
import com.elvishew.xlog.XLog
import com.hjq.toast.ToastUtils
import com.tencent.smtt.sdk.QbSdk
import com.www.net.LvCreator

class WanApplication : Application() {
    override fun onCreate() {
        super.onCreate()

        XLog.init(LogConfiguration.Builder().t().tag("345").build())
        ToastUtils.init(this)
        LvCreator
            .init("https://www.wanandroid.com/")
            .log(false)

        initX5()
    }

    private fun initX5() {
        val cb = object : QbSdk.PreInitCallback {
            override fun onCoreInitFinished() {
            }

            override fun onViewInitFinished(p0: Boolean) {
                //x5内核初始化完成的回调，为true表示内个加载成功
                Log.d("X5：", "--- $p0")
            }
        }
        //x5 初始化
        QbSdk.initX5Environment(this, cb)
    }
}
