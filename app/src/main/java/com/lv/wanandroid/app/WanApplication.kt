package com.lv.wanandroid.app

import android.app.Application
import android.util.Log
import com.elvishew.xlog.LogConfiguration
import com.elvishew.xlog.XLog
import com.hjq.toast.ToastUtils
import com.igexin.sdk.IUserLoggerInterface
import com.igexin.sdk.PushManager
import com.lv.wanandroid.services.WanPusService
import com.meituan.android.walle.WalleChannelReader
import com.tencent.smtt.sdk.QbSdk
import com.umeng.commonsdk.UMConfigure
import com.umeng.socialize.PlatformConfig
import com.www.net.LvCreator


class WanApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        //初始化个推SDK
        PushManager.getInstance().initialize(applicationContext, WanPusService::class.java)
        //验证推送
        PushManager.getInstance().setDebugLogger(
            this
        ) { p0 -> Log.i("PUSH_LOG", p0) }

        init()
        initX5()
    }

    private fun init() {

        //友盟 和 多渠道
        val channel = WalleChannelReader.getChannel(applicationContext)
        UMConfigure.init(this, "", channel, UMConfigure.DEVICE_TYPE_PHONE, "")
        UMConfigure.setLogEnabled(true)
        /**
         * 友盟相关平台配置。注意友盟官方新文档中没有这项配置，但是如果不配置会吊不起来相关平台的授权界面
         */
//        PlatformConfig.setWeixin("你的微信APPID", "你的微信AppSecret");//微信APPID和AppSecret
        PlatformConfig.setQQZone(
            "101863419",
            "1f5f800811f56bef4c75a58fdf7a3921"
        );//QQAPPID和AppSecret
//        PlatformConfig.setSinaWeibo("你的微博APPID", "你的微博APPSecret","微博的后台配置回调地址");//微博


        //log 和 Tost 工具
        XLog.init(LogConfiguration.Builder().t().tag("345").build())
        ToastUtils.init(this)

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
        QbSdk.initX5Environment(this, cb)
    }

}
