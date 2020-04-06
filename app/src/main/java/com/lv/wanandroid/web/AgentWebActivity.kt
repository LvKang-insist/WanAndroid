package com.lv.wanandroid.web

import android.Manifest
import android.content.Intent
import android.os.Build
import android.webkit.WebView
import android.widget.LinearLayout
import androidx.core.app.ActivityCompat
import com.hjq.toast.ToastUtils
import com.just.agentweb.AgentWeb
import com.just.agentweb.DefaultWebClient
import com.just.agentweb.WebChromeClient
import com.lv.wanandroid.base.BaseWebAgentActivity
import com.lv.wanandroid.web.mvp.WebContract
import com.lv.wanandroid.web.mvp.WebPresenter
import com.umeng.socialize.ShareAction
import com.umeng.socialize.UMShareAPI
import com.umeng.socialize.UMShareListener
import com.umeng.socialize.bean.SHARE_MEDIA
import com.umeng.socialize.media.UMImage
import com.umeng.socialize.media.UMWeb
import kotlinx.android.synthetic.main.frag_agent_web.*


/**
 * @name WanAndroid-kotlin
 * @class name：com.lv.wanandroid.web
 * @author 345 QQ:1831712732
 * @time 2020/3/27 22:07
 * @description https://www.jianshu.com/p/748862fc929f
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
        return com.lv.wanandroid.R.layout.frag_agent_web

    }

    override fun initExtra(intent: Intent) {
        link = intent.getStringExtra("link")
    }

    override fun bindView() {
        initBar(frag_agent_toolbar)
        initWeb()
        frag_agent_back.setOnClickListener {
            if (mAgentWeb.webCreator.webView.canGoBack()) {
                mAgentWeb.webCreator.webView.goBack()
            } else {
                finish()
            }
        }

        val umWeb = UMWeb(link)
        umWeb.title = frag_agent_title.text.toString()
        umWeb.setThumb(UMImage(this, com.lv.wanandroid.R.drawable.error_img))
        umWeb.description = "我是描述"
        frag_agent_share.setOnClickListener {

            if (Build.VERSION.SDK_INT >= 23) {
                val mPermissionList = arrayOf<String>(
                    Manifest.permission.WRITE_EXTERNAL_STORAGE,
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.CALL_PHONE,
                    Manifest.permission.READ_LOGS,
                    Manifest.permission.READ_PHONE_STATE,
                    Manifest.permission.READ_EXTERNAL_STORAGE,
                    Manifest.permission.SET_DEBUG_APP,
                    Manifest.permission.SYSTEM_ALERT_WINDOW,
                    Manifest.permission.GET_ACCOUNTS,
                    Manifest.permission.WRITE_APN_SETTINGS
                )
                ActivityCompat.requestPermissions(this, mPermissionList, 123)
            }


            ShareAction(this)
                .setDisplayList(
                    SHARE_MEDIA.QQ,
                    SHARE_MEDIA.QZONE,
                    SHARE_MEDIA.WEIXIN,
                    SHARE_MEDIA.WEIXIN_CIRCLE
                )
                .setShareboardclickCallback { _, shareMedia ->
                    when (shareMedia) {
                        SHARE_MEDIA.QQ -> {
                            ShareAction(this)
                                .setPlatform(SHARE_MEDIA.QQ)
                                .withMedia(umWeb)
                                .setCallback(object : UMShareListener {
                                    override fun onResult(p0: SHARE_MEDIA?) {
                                        ToastUtils.show("成功了")
                                    }

                                    override fun onCancel(p0: SHARE_MEDIA?) {
                                        ToastUtils.show("分享取消了")
                                    }

                                    override fun onError(p0: SHARE_MEDIA?, p1: Throwable?) {
                                        ToastUtils.show("分享失败了")
                                    }

                                    override fun onStart(p0: SHARE_MEDIA?) {
                                        // 分享开始的回调
                                    }

                                })
                                .share()
                        }
                        SHARE_MEDIA.WEIXIN -> {

                        }
                        SHARE_MEDIA.QZONE -> {

                        }
                        SHARE_MEDIA.WEIXIN_CIRCLE -> {

                        }
                        else -> {
                        }
                    }
                }.open()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        UMShareAPI.get(this).onActivityResult(requestCode, resultCode, data)
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
                    frag_agent_title.text = title
                }
            })
            //参数1是错误显示的布局，参数2点击刷新控件ID -1表示点击整个布局都刷新， AgentWeb 3.0.0 加入。
            .setMainFrameErrorView(com.lv.wanandroid.R.layout.agentweb_error_page, -1)
            //打开其他页面时，弹窗质询用户前往其他应用 AgentWeb 3.0.0 加入。
            .setOpenOtherPageWays(DefaultWebClient.OpenOtherPageWays.DISALLOW)
            .createAgentWeb()
            .ready()
            .go(link)

    }

}