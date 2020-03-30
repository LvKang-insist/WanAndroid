package com.lv.wanandroid.base

import android.annotation.SuppressLint
import android.app.PendingIntent.getActivity
import android.os.Bundle
import android.view.KeyEvent
import android.view.Menu
import android.view.View
import android.webkit.WebSettings
import android.webkit.WebView
import com.just.agentweb.AgentWeb
import com.just.agentweb.AgentWebConfig
import com.lv.core.mvp.IContract
import com.lv.wanandroid.module.home.mvp.HomeContract
import java.util.*

/**
 * @name WanAndroid-kotlin
 * @class name：com.lv.wanandroid.base
 * @author 345 QQ:1831712732
 * @time 2020/3/27 22:35
 * @description
 */
abstract class BaseWebAgentActivity<V : IContract.IBaseView, P : IContract.IBasePresenter<V>> :
    BaseActivity<V, P>(), IContract.IBaseView {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        init()
    }

    @SuppressLint("SetJavaScriptEnabled")
    private fun init() {
        val mAgentWeb = getAgentWeb()
        AgentWebConfig.debug()


        // AgentWeb 没有把WebView的功能全面覆盖 ，所以某些设置 AgentWeb 没有提供 ， 请从WebView方面入手设置。
        mAgentWeb.webCreator.webView.overScrollMode = WebView.OVER_SCROLL_NEVER

        mAgentWeb.webCreator.webView.settings.javaScriptEnabled = true
        //优先使用网络
        mAgentWeb.webCreator.webView.settings.cacheMode = WebSettings.LOAD_CACHE_ELSE_NETWORK
        //将图片调整到适合webView的大小
        mAgentWeb.webCreator.webView.settings.useWideViewPort = true
        //支持内容重新布局
        mAgentWeb.webCreator.webView.settings.layoutAlgorithm =
            WebSettings.LayoutAlgorithm.SINGLE_COLUMN
        //支持自动加载图片
        mAgentWeb.webCreator.webView.settings.loadsImagesAutomatically = true
        //当webView调用requestFocus时为webView设置节点
        mAgentWeb.webCreator.webView.settings.setNeedInitialFocus(true)
        //自适应屏幕
        mAgentWeb.webCreator.webView.settings.useWideViewPort = true
        mAgentWeb.webCreator.webView.settings.loadWithOverviewMode = true
        //开启DOM storage API功能（HTML5 提供的一种标准的接口，主要将键值对存储在本地，在页面加载完毕后可以通过 javascript 来操作这些数据。）
        mAgentWeb.webCreator.webView.settings.domStorageEnabled = true
        //支持缩放
        mAgentWeb.webCreator.webView.settings.builtInZoomControls = true
        mAgentWeb.webCreator.webView.settings.setSupportZoom(true)

        //允许webView对文件的操作
        mAgentWeb.webCreator.webView.settings.allowFileAccess = true
        mAgentWeb.webCreator.webView.settings.allowFileAccessFromFileURLs = true
        mAgentWeb.webCreator.webView.settings.allowUniversalAccessFromFileURLs = true
        mAgentWeb.webCreator.webView.setOnKeyListener(object : View.OnKeyListener {
            override fun onKey(v: View?, keyCode: Int, event: KeyEvent?): Boolean {
                if (event?.action == KeyEvent.ACTION_DOWN) {
                    if (keyCode == KeyEvent.KEYCODE_BACK && mAgentWeb.webCreator.webView.canGoBack()) { // 表示按返回键时的操作
                        mAgentWeb.webCreator.webView.goBack() // 后退
                        // webView.goForward()//前进
                        return true // 已处理
                    } else if (keyCode == KeyEvent.KEYCODE_BACK) {
//                        moveTaskToBack(true)
                        finish()
                    }
                }
                return false
            }

        })
    }

    abstract fun getAgentWeb(): AgentWeb

    override fun onResume() {
        getAgentWeb().webLifeCycle.onResume()
        super.onResume()
    }

    override fun onPause() {
        getAgentWeb().webLifeCycle.onPause()
        super.onPause()
    }

    override fun onDestroy() {
        getAgentWeb().webLifeCycle.onDestroy()
        super.onDestroy()
    }

}