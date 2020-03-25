package com.lv.wanandroid.app

import android.app.Application
import com.elvishew.xlog.LogConfiguration
import com.elvishew.xlog.XLog
import com.hjq.toast.ToastUtils
import com.www.net.LvCreator

class WanApplication : Application() {
    override fun onCreate() {
        super.onCreate()

        XLog.init(LogConfiguration.Builder().t().tag("345").build())
        ToastUtils.init(this)
        LvCreator
            .init("https://www.wanandroid.com/")
            .log(false)
    }
}
