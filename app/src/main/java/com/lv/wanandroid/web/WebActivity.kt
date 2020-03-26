package com.lv.wanandroid.web

import android.content.Intent
import android.view.View
import com.hjq.toast.ToastUtils
import com.lv.wanandroid.R
import com.lv.wanandroid.base.BaseWebActivity
import com.lv.wanandroid.web.mvp.WebContract
import com.lv.wanandroid.web.mvp.WebPresenter
import com.tencent.smtt.export.external.interfaces.IX5WebChromeClient
import com.tencent.smtt.sdk.WebChromeClient
import com.tencent.smtt.sdk.WebView
import kotlinx.android.synthetic.main.frag_web.*

class WebActivity : BaseWebActivity<WebContract.View, WebContract.Presenter>(), WebContract.View {

    override fun createPresenter(): WebContract.Presenter {
        return WebPresenter()
    }
    override fun getWebX5(): WebView {
        return web_x5
    }
    override fun layoutId(): Int {
        return R.layout.frag_web
    }

    override fun initExtra(intent: Intent) {
        val link = intent.getStringExtra("link")
        ToastUtils.show(link)
        web_x5.loadUrl("https://mp.weixin.qq.com/s/8v9bJmaOSFrHACfHLinH9Q")
        web_x5.webChromeClient = object : WebChromeClient() {
            override fun onShowCustomView(p0: View?, p1: IX5WebChromeClient.CustomViewCallback?) {

            }
        }
    }

    override fun bindView() {

    }

    override fun onBackPressed() {
        if (web_x5.canGoBack()) {
            web_x5.goBack()
        } else {
            finish()
        }
    }
}
