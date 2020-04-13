package com.lv.wanandroid.app

import android.app.Application
import android.content.Context
import android.content.Intent
import android.util.Log
import androidx.multidex.MultiDex
import com.elvishew.xlog.LogConfiguration
import com.elvishew.xlog.XLog
import com.hjq.toast.ToastUtils
import com.igexin.sdk.PushManager
import com.lv.wanandroid.services.WanPusService
import com.meituan.android.walle.WalleChannelReader
import com.tencent.bugly.Bugly
import com.tencent.bugly.Bugly.applicationContext
import com.tencent.bugly.beta.Beta
import com.tencent.smtt.sdk.QbSdk
import com.tencent.tinker.entry.DefaultApplicationLike
import com.umeng.commonsdk.UMConfigure
import com.umeng.socialize.PlatformConfig
import com.umeng.socialize.UMShareAPI
import com.www.net.LvCreator


class SampleApplicationLike(
    application: Application,
    tinkerFlags: Int,
    tinkerLoadVerifyFlag: Boolean,
    applicationStartElapsedTime: Long,
    applicationStartMillisTime: Long,
    tinkerResultIntent: Intent
) : DefaultApplicationLike(
    application,
    tinkerFlags,
    tinkerLoadVerifyFlag,
    applicationStartElapsedTime,
    applicationStartMillisTime,
    tinkerResultIntent
) {

    val TAG = "Tinker.SampleApplicationLike"

    override fun onCreate() {
        super.onCreate()
        // 这里实现SDK初始化，appId替换成你的在Bugly平台申请的appId
        // 调试时，将第三个参数改为true
        Bugly.init(application, "4978c94036", true)

        //初始化个推SDK
        PushManager.getInstance().initialize(applicationContext, WanPusService::class.java)
        //验证推送
        PushManager.getInstance().setDebugLogger(
            application
        ) { p0 -> Log.i("PUSH_LOG", p0) }

        init()
        initX5()
    }

    override fun onBaseContextAttached(base: Context?) {
        super.onBaseContextAttached(base)

        // you must install multiDex whatever tinker is installed!
        MultiDex.install(base);
        // 安装tinker
        // TinkerManager.installTinker(this); 替换成下面Bugly提供的方法
        Beta.installTinker(this);
    }

    fun registerActivityLifecycleCallback(callbacks: Application.ActivityLifecycleCallbacks) {
        application.registerActivityLifecycleCallbacks(callbacks)
    }

    private fun init() {

        //友盟 和 多渠道
        val channel = WalleChannelReader.getChannel(applicationContext)
        UMConfigure.init(
            application,
            "5e8586b7dbc2ec080a6d1291",
            channel,
            UMConfigure.DEVICE_TYPE_PHONE,
            ""
        )
        UMConfigure.setLogEnabled(true)
        /**
         * 友盟相关平台配置。注意友盟官方新文档中没有这项配置，但是如果不配置会吊不起来相关平台的授权界面
         */
//        PlatformConfig.setWeixin("你的微信APPID", "你的微信AppSecret");//微信APPID和AppSecret
        UMShareAPI.get(application)
        PlatformConfig.setQQZone(
            "101863419",
            "1f5f800811f56bef4c75a58fdf7a3921"
        )//QQAPPID和AppSecret
//        PlatformConfig.setSinaWeibo("你的微博APPID", "你的微博APPSecret","微博的后台配置回调地址");//微博


        //log 和 Tost 工具
        XLog.init(LogConfiguration.Builder().t().tag("345").build())
        ToastUtils.init(application)

        //网络库初始化
        LvCreator
            .init("https://www.wanandroid.com/")
            .log(false)

//        val config = AppWatcher.config.
//            copy(watchFragmentViews = false)
//        AppWatcher.config = config
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
        QbSdk.initX5Environment(application, cb)
    }
}