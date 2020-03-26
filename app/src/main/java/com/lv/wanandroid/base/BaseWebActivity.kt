package com.lv.wanandroid.base

import android.os.Bundle
import com.lv.core.mvp.IContract
import com.tencent.smtt.sdk.WebView
import com.tencent.smtt.sdk.WebViewClient

abstract class BaseWebActivity<V : IContract.IBaseView, P : IContract.IBasePresenter<V>> :
    BaseActivity<V, P>(), IContract.IBaseView {

    private lateinit var webX5: WebView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        webX5 = getWebX5()

        init()
        listener()
    }
//    https://www.jianshu.com/p/7e101c33179e
    fun listener() {
        //从该页面打开更多链接
        webX5.webViewClient = object : WebViewClient() {
            override fun shouldOverrideUrlLoading(p0: WebView?, p1: String?): Boolean {
                webX5.loadUrl(p1)
                return true
            }
        }
    }

    fun init() {
        webX5.getSettings().setSupportZoom(true); //支持缩放，默认为true。是下面那个的前提。
        webX5.getSettings().setBuiltInZoomControls(true); //设置内置的缩放控件。若为false，则该WebView不可缩放
        webX5.getSettings().setDisplayZoomControls(true); //隐藏原生的缩放控件
        webX5.getSettings().setBlockNetworkImage(false);//解决图片不显示
        webX5.getSettings().setLoadsImagesAutomatically(true); //支持自动加载图片
        webX5.getSettings().setDefaultTextEncodingName("utf-8");//设置编码格式
    }


    abstract fun getWebX5(): WebView
}
