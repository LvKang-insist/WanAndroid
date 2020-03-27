package com.lv.wanandroid.web

import android.content.Intent
import android.view.View
import androidx.appcompat.widget.Toolbar
import com.lv.wanandroid.R
import com.lv.wanandroid.base.BaseWebActivity
import com.lv.wanandroid.web.mvp.WebContract
import com.lv.wanandroid.web.mvp.WebPresenter
import com.tencent.smtt.sdk.WebView
import kotlinx.android.synthetic.main.frag_web.*

class WebActivity : BaseWebActivity<WebContract.View, WebContract.Presenter>(), WebContract.View {
    var link: String? = null
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
        link = intent.getStringExtra("link")
//        web_x5.loadUrl()
//        "https://mp.weixin.qq.com/s/8v9bJmaOSFrHACfHLinH9Q"
    }

    override fun onStart() {
        super.onStart()
        web_x5.loadUrl(link)
        web_x5.view.overScrollMode = View.OVER_SCROLL_ALWAYS

    }

    override fun bindView() {
        setSupportActionBar(tb_web)
        supportActionBar?.title = ""
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        tb_web.setNavigationIcon(R.drawable.ic_back)
        tb_web?.setNavigationOnClickListener {
            goBack()
        }
    }

    override fun onBackPressed() {
        if (web_x5.canGoBack()) {
            web_x5.goBack()
        } else {
            finish()
        }
    }

    override fun getToolbar(): Toolbar {
        return tb_web
    }

    /**
     * 返回
     */
    private fun goBack() {
        val canGoBack = web_x5?.canGoBack() ?: false
        if (canGoBack) {
            web_x5?.goBack()
        } else {
            finish()
        }
    }
}
