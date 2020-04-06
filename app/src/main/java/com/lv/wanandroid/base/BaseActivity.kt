package com.lv.wanandroid.base

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.gyf.immersionbar.ImmersionBar
import com.lv.core.mvp.IContract
import com.umeng.analytics.MobclickAgent

/**
 * @name WanAndroid-kotlin
 * @class name：com.lv.wanandroid.main
 * @author 345 QQ:1831712732
 * @time 2020/3/24 22:59
 * @description
 */

@Suppress("UNCHECKED_CAST")
abstract class BaseActivity<V : IContract.IBaseView, P : IContract.IBasePresenter<V>> :
    AppCompatActivity(), IContract.IBaseView {

    lateinit var mPresenter: P
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(layoutId())

        //实现灰度化
       /* val paint = Paint()
        val cm = ColorMatrix()
        cm.setSaturation(0f)
        paint.colorFilter = ColorMatrixColorFilter(cm)
        window.decorView.setLayerType(View.LAYER_TYPE_HARDWARE, paint)*/

        mPresenter = createPresenter()
        mPresenter.attachView(this as V, savedInstanceState)
        //P 层感知生命周期
        lifecycle.addObserver(mPresenter)
        initExtra(intent)
        bindView()
    }

    /**
     * 实现界面灰度
     */
    /* override fun onCreateView(name: String, context: Context, attrs: AttributeSet): View? {
         if ("FrameLayout" == name) {
             val count = attrs.attributeCount
             for (i in 0 until count) {
                 val attributeName = attrs.getAttributeName(i)
                 val attributeValue = attrs.getAttributeValue(i)
                 if (attributeName == "id") {
                     val id = attributeValue.substring(1).toInt()
                     val idVal = resources.getResourceName(id)
                     if ("android:id/content".equals(idVal)) {
                         return GrayFrameLayout(context, attrs)
                     }
                 }
             }
         }
         return super.onCreateView(name, context, attrs)
     }*/

    /**
     * 初始化状态栏，可更具需求重写
     */
    fun initBar(toolbar: View) {
        ImmersionBar.with(this)
            //解决状态栏白色的问题，如果不支持变色，则加上透明度
            .statusBarDarkFont(true, 0.2f)
            .titleBar(toolbar)
            .init()
    }

    override fun onResume() {
        super.onResume()
        //友盟统计
        MobclickAgent.onResume(this)
    }

    override fun onPause() {
        super.onPause()
        MobclickAgent.onPause(this)
    }

    open fun initExtra(intent: Intent) {}

    abstract fun createPresenter(): P

    abstract fun layoutId(): Int

    abstract fun bindView()

}