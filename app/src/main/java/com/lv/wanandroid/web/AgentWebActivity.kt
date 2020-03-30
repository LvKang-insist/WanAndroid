package com.lv.wanandroid.web

import android.content.Intent
import android.webkit.WebView
import android.widget.LinearLayout
import com.just.agentweb.*
import com.lv.wanandroid.R
import com.lv.wanandroid.base.BaseWebAgentActivity
import com.lv.wanandroid.web.mvp.WebContract
import com.lv.wanandroid.web.mvp.WebPresenter
import kotlinx.android.synthetic.main.frag_agent_web.*


/**
 * @name WanAndroid-kotlin
 * @class name：com.lv.wanandroid.web
 * @author 345 QQ:1831712732
 * @time 2020/3/27 22:07
 * @description
 */
class AgentWebActivity : BaseWebAgentActivity<WebContract.View, WebContract.Presenter>(),
    WebContract.View {
    var link: String? = null
    lateinit var mAgentWeb: AgentWeb
    override fun createPresenter(): WebContract.Presenter {
        return WebPresenter()
    }

    override fun getAgentWeb(): AgentWeb {
        return mAgentWeb
    }

    override fun layoutId(): Int {
        return R.layout.frag_agent_web

    }

    override fun initExtra(intent: Intent) {
        link = intent.getStringExtra("link")
    }

    override fun bindView() {
        initWeb()
        setSupportActionBar(frag_agent_toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        frag_agent_toolbar.setNavigationIcon(R.drawable.ic_back)
        frag_agent_toolbar?.setNavigationOnClickListener {
            if (mAgentWeb.webCreator.webView.canGoBack()) {
                mAgentWeb.webCreator.webView.goBack()
            } else {
                finish()
            }
        }
    }

    private fun initWeb() {
        mAgentWeb = AgentWeb.with(this)
            .setAgentWebParent(
                frag_agent_linear!!, LinearLayout.LayoutParams(-1, -1)
            )
            //设置进度条颜色与高度，-1为默认值，高度为2，单位为dp。
//            .useDefaultIndicator(-1, 3)
            .setCustomIndicator(CoolIndicatorLayout(this))
            //严格模式 Android 4.2.2 以下会放弃注入对象 ，使用AgentWebView没影响。
            .setSecurityType(AgentWeb.SecurityType.STRICT_CHECK)
            .setWebChromeClient(object : WebChromeClient() {
                override fun onReceivedTitle(view: WebView?, title: String?) {
                    frag_agent_toolbar.title = title
                }
            })
            //参数1是错误显示的布局，参数2点击刷新控件ID -1表示点击整个布局都刷新， AgentWeb 3.0.0 加入。
            .setMainFrameErrorView(R.layout.agentweb_error_page, -1)
            //打开其他页面时，弹窗质询用户前往其他应用 AgentWeb 3.0.0 加入。
            .setOpenOtherPageWays(DefaultWebClient.OpenOtherPageWays.DISALLOW)
            .createAgentWeb()
            .ready()
            .go(link)

    }


}