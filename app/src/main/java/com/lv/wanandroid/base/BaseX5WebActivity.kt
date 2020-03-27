package com.lv.wanandroid.base

import android.annotation.SuppressLint
import android.graphics.PixelFormat
import android.os.Build
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.appcompat.widget.Toolbar
import com.elvishew.xlog.XLog
import com.lv.core.mvp.IContract
import com.lv.wanandroid.R
import com.tencent.smtt.export.external.interfaces.ConsoleMessage
import com.tencent.smtt.export.external.interfaces.IX5WebChromeClient
import com.tencent.smtt.export.external.interfaces.JsPromptResult
import com.tencent.smtt.export.external.interfaces.JsResult
import com.tencent.smtt.sdk.*


abstract class BaseX5WebActivity<V : IContract.IBaseView, P : IContract.IBasePresenter<V>> :
    BaseActivity<V, P>(), IContract.IBaseView {

    private lateinit var webX5: WebView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        webX5 = getWebX5()

        init()
//        listener()
    }

    @SuppressLint("SetJavaScriptEnabled")
    private fun init() {

        //视频为了避免闪屏和透明问题
        window.setFormat(PixelFormat.TRANSLUCENT)

        webX5.settings.setSupportZoom(true) //支持缩放，默认为true。是下面那个的前提。
        webX5.settings.builtInZoomControls = true //设置内置的缩放控件。若为false，则该WebView不可缩放
        webX5.settings.displayZoomControls = true //隐藏原生的缩放控件
        webX5.settings.blockNetworkImage = false//解决图片不显示
        webX5.settings.loadsImagesAutomatically = true //支持自动加载图片
        webX5.settings.defaultTextEncodingName = "utf-8"//设置编码格式
        webX5.settings.javaScriptCanOpenWindowsAutomatically = true
        webX5.settings.javaScriptEnabled = true
        webX5.settings.useWideViewPort = true    //将图片调整大小适合 webView 的大小
        webX5.settings.loadWithOverviewMode = true   //缩放至屏幕的大小
        webX5.settings.blockNetworkLoads = false
        webX5.overScrollMode = View.OVER_SCROLL_ALWAYS
        //文件权限
        webX5.settings.allowFileAccess = true
        webX5.settings.setAllowFileAccessFromFileURLs(true)
        webX5.settings.setAllowUniversalAccessFromFileURLs(true)
        webX5.settings.allowContentAccess = true
        //缓存相关
        webX5.settings.setAppCacheEnabled(true)
        webX5.settings.domStorageEnabled = true
        webX5.settings.databaseEnabled = true
        if (isNoWebCache()) {
            webX5.settings.cacheMode = WebSettings.LOAD_NO_CACHE
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            webX5.settings.mixedContentMode = android.webkit.WebSettings.MIXED_CONTENT_ALWAYS_ALLOW
        }
//        configPlaySetting()
        //从该页面打开更多链接
        webX5.webViewClient = object : WebViewClient() {
            override fun shouldOverrideUrlLoading(p0: WebView, p1: String): Boolean {
                p0.loadUrl(p1)
                return true
            }
        }
    }

    private fun listener() {

        webX5.webChromeClient = object : WebChromeClient() {
            override fun onProgressChanged(p0: WebView?, p1: Int) {
                XLog.d("当前 腾讯X5 进度 $p1")
            }

            override fun onJsPrompt(
                p0: WebView, p1: String, p2: String, p3: String, p4: JsPromptResult
            ): Boolean {
                return handleJsPrompt(p0, p1, p2, p3, p4)
            }

            override fun onJsAlert(p0: WebView?, p1: String?, p2: String?, p3: JsResult?): Boolean {
                return handleJsAlert(p0, p1, p2, p3)
            }

            override fun onJsConfirm(
                p0: WebView?, p1: String?, p2: String?, p3: JsResult?
            ): Boolean {
                return handleJsConfirm(p0, p1, p2, p3)
            }

            override fun onConsoleMessage(p0: ConsoleMessage): Boolean {
                XLog.d(
                    javaClass.name, String.format(
                        "[%s] sourceID: %s lineNumber: %s message: %s",
                        p0.messageLevel(), p0.sourceId(), p0.lineNumber(), p0.message()
                    )
                )
                return super.onConsoleMessage(p0)
            }

            override fun onReceivedTitle(webView: WebView?, s: String?) {
                super.onReceivedTitle(webView, s)
                getToolbar()?.title = s
            }

            var myView: View? = null

            //自定义视频播放  如果需要启用这个，需要设置x5,自己实现全屏播放。目前的使用的x5的视频播放
            //如果是点击h5 Video 标签的播放，需要自己实现全屏播放
            override fun onShowCustomView(view: View, p1: IX5WebChromeClient.CustomViewCallback?) {
                super.onShowCustomView(view, p1)
                val parent = webX5.parent as ViewGroup
                parent.removeView(webX5)

                view.setBackgroundColor(application.resources.getColor(R.color.black))
                parent.addView(view)
                myView = view
                getToolbar()?.visibility = View.GONE
                setFullScreen()
            }

            override fun onHideCustomView() {
                super.onHideCustomView()
                if (myView != null) {
                    val parent = myView as ViewGroup
                    parent.removeView(myView)
                    parent.addView(webX5)
                    myView = null
                    getToolbar()?.visibility = View.VISIBLE
                    quitFullScreen()
                }
            }
        }

    }

    private fun configPlaySetting() {
        val data = Bundle()
        //true表示标准全屏，false表示X5全屏；不设置默认false，
        //standardFullScreen 全屏设置
        //设置为true时，我们会回调WebChromeClient的onShowCustomView方法，由开发者自己实现全屏展示；
        //设置为false时，由我们实现全屏展示，我们实现全屏展示需要满足下面两个条件：
        //a. 我们 WebView 初始化的Context必须是Activity类型的Context
        //b. 我们 Webview 所在的Activity要声明这个属性
        // android:configChanges="orientation|screenSize|keyboardHidden"
        //如果不满足这两个条件，standardFullScreen 自动置为 true
        data.putBoolean("standardFullScreen", false)
        //false：关闭小窗；true：开启小窗；不设置默认true，前提standardFullScreen=false，这个条件才生效
        data.putBoolean("supportLiteWnd", true)
        //1：以页面内开始播放，2：以全屏开始播放；不设置默认：1
        data.putInt("DefaultVideoScreen", 1)
//        webX5.x5WebViewExtension.invokeMiscMethod("setVideoParams", data)
    }

    /**
     *设置全屏
     */
    private fun setFullScreen() {
        this.window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )
    }

    /**
     * 退出全屏
     */
    private fun quitFullScreen() {
        // 声明当前屏幕状态的参数并获取
        val attrs = this.window.attributes
        attrs.flags = attrs.flags and WindowManager.LayoutParams.FLAG_FULLSCREEN.inv()
        this.window.attributes = attrs
        this.window.clearFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS)
    }

    protected fun playVideoByTbs(videoUrl: String): Boolean {
        return if (TbsVideo.canUseTbsPlayer(this)) {
            //播放器是否可以使用
            val xtraData = Bundle()
            xtraData.putInt("screenMode", 102)//全屏设置 和控制栏设置
            TbsVideo.openVideo(this, videoUrl, xtraData)
            true
        } else {
            false
        }
    }


    /**
     * 是否在android中处理Js的Prompt弹出框
     * false不处理，true可拦截处理
     */
    protected fun handleJsPrompt(
        view: WebView, url: String, message: String, defaultValue: String, result: JsPromptResult
    ): Boolean {
        return false
    }

    /**
     * 是否在android中处理Js的Alert弹出框
     * false不处理，true可拦截处理
     */
    protected open fun handleJsAlert(
        view: WebView?, url: String?, message: String?, result: JsResult?
    ): Boolean {
        return false
    }

    /**
     * 是否在android中处理Js的Confirm弹出框
     * false不处理，true可拦截处理
     */
    protected open fun handleJsConfirm(
        view: WebView?, url: String?, message: String?, result: JsResult?
    ): Boolean {
        return false
    }

    /**
     * 不用缓存,true:无缓存,false:默认
     *
     * @return
     */
    protected open fun isNoWebCache(): Boolean {
        return false
    }


    /**
     * 退出界面暂停 webView的活跃
     */
    override fun onPause() {
        super.onPause()
        webX5.onPause()
        webX5.settings.lightTouchEnabled = false
    }

    override fun onDestroy() {
        webX5.stopLoading()
        webX5.removeAllViews()
        webX5.destroy()
        super.onDestroy()
        XLog.d("X5: Web 已销毁")
    }


    abstract fun getWebX5(): WebView

    abstract fun getToolbar(): Toolbar?

}
