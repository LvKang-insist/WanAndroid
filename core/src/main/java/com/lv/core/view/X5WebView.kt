package com.lv.core.view

import android.annotation.SuppressLint
import android.content.Context
import android.util.AttributeSet
import com.tencent.smtt.sdk.WebSettings
import com.tencent.smtt.sdk.WebView
import com.tencent.smtt.sdk.WebViewClient

interface WebViewJavaScriptFunction{
     fun onJsFunctionCalled(tag: String)
}

class X5WebView : WebView {

    constructor(context: Context) : super(context) {
        setBackgroundColor(85621)
    }

    constructor(context: Context, attributeSet: AttributeSet) : super(context, attributeSet) {
        webViewClient = object : WebViewClient() {
            override fun shouldOverrideUrlLoading(p0: WebView, p1: String): Boolean {
                p0.loadUrl(p1)
                return true
            }
        }
        initWebViewSettings()
        this.view.isClickable = true
    }

    @SuppressLint("SetJavaScriptEnabled")
    private fun initWebViewSettings() {
        settings.javaScriptEnabled = true
        settings.javaScriptCanOpenWindowsAutomatically = true
        settings.allowFileAccess = true
        settings.layoutAlgorithm = WebSettings.LayoutAlgorithm.NARROW_COLUMNS
        settings.setSupportZoom(true)
        settings.builtInZoomControls = true
        settings.useWideViewPort = true
        settings.setSupportMultipleWindows(true)
        settings.setAppCacheEnabled(true)
        settings.domStorageEnabled = true
        settings.setGeolocationEnabled(true)
        settings.setAppCacheMaxSize(java.lang.Long.MAX_VALUE)
        settings.pluginState = WebSettings.PluginState.ON_DEMAND
        settings.cacheMode = WebSettings.LOAD_NO_CACHE
    }
}