@file:Suppress("DEPRECATION")

package com.lv.core.utils

import android.Manifest
import android.annotation.SuppressLint
import android.annotation.TargetApi
import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import android.graphics.Point
import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.os.Build
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.view.WindowManager
import androidx.annotation.RequiresApi


/**
 * @author 345 QQ:1831712732
 * @name Android Business Toos
 * @class name：com.business.tools.utils
 * @time 2019/12/23 21:22
 * @description
 */
object ToolsUtils {


    private val appContext: Context
        get() = AppContext.getApplication()

    /**
     * @return 可用显示大小的绝对宽度(以像素为单位)
     */
    val screenWidth: Int
        get() {
            val resources = appContext.resources
            val dm = resources.displayMetrics

            return dm.widthPixels
        }

    /**
     * @return 可用显示大小的绝对高度(以像素为单位)
     */
    val screenHeight: Int
        get() {
            val resources = appContext.resources
            val dm = resources.displayMetrics
            return dm.heightPixels
        }


    /**
     * @return 返回 状态栏的 高度，以像素为单位
     */
    val staticBarHeight: Int
        get() {
            var result = 0
            val resourceId = appContext.resources.getIdentifier(
                "status_bar_height",
                "dimen", "android"
            )
            if (resourceId > 0) {
                result = appContext.resources.getDimensionPixelSize(resourceId)
            }
            return result
        }

    /**
     * 获取屏幕宽度
     *
     * @param activity activity
     * @return 屏幕宽度
     */
    fun getScreenWidth(activity: Activity): Int {
        val defaultDisplay = activity.windowManager.defaultDisplay
        val point = Point()
        defaultDisplay.getSize(point)
        return point.x
    }

    /**
     * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
     */
    fun dip2px(dpValue: Float): Int {
        val scale = appContext.resources.displayMetrics.density
        return (dpValue * scale + 0.5f).toInt()
    }

    /**
     * 根据手机的分辨率从 px(像素) 的单位 转成为 dp
     */
    fun px2dip(pxValue: Float): Int {
        val scale = appContext.resources.displayMetrics.density
        return (pxValue / scale + 0.5f).toInt()
    }


    /**
     * 判断网络是否连接
     */
    val isNetWorkAvailable: Boolean
        @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
        get() {
            if (appContext.checkCallingOrSelfPermission(Manifest.permission.INTERNET)
                != PackageManager.PERMISSION_GRANTED
            ) {
                return false
            } else {
                val connectivity = appContext
                    .getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager;
                val info: Array<NetworkInfo> = connectivity.allNetworkInfo
                info.forEach {
                    if (it.isAvailable) {
                        return true
                    }
                }
            }
            return false
        }

    /**
     * 判断是否存在 NavigationBar
     */
    val checkDeviceHasNavigationBar: Boolean
        @SuppressLint("PrivateApi")
        get() {
            var hasNavigationBar = false
            val rs = appContext.resources
            val id = rs.getIdentifier("config_showNavigationBar", "bool", "android")
            if (id > 0) {
                hasNavigationBar = rs.getBoolean(id)
            }
            try {
                val systemPropertiesClass = Class.forName("android.os.SystemProperties")
                val m = systemPropertiesClass.getMethod("get", String::class.java)
                val navBarOverride = m.invoke(systemPropertiesClass, "qemu.hw.mainkeys") as String
                if ("1" == navBarOverride) {
                    //不存在虚拟按键
                    hasNavigationBar = false
                } else if ("0" == navBarOverride) {
                    //存在虚拟按键
                    hasNavigationBar = true
                }
            } catch (e: Exception) {
            }
            return hasNavigationBar
        }

    /**
     * 获取虚拟按键的高度
     */
    @SuppressLint("NewApi")
    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    fun getNavigationBarHeight(activity: Activity): Int {
        val systemUiVisible = isSystemUiVisible(activity.getWindow())
        if (!systemUiVisible[1]) {
            return 0
        }
        var result = 0
        if (checkDeviceHasNavigationBar) {
            val res = AppContext.getApplication().resources
            val resourceId = res.getIdentifier("navigation_bar_height", "dimen", "android")
            if (resourceId > 0) {
                result = res.getDimensionPixelSize(resourceId)
            }
        }
        return result
    }

    /**
     * 判断 底部虚拟导航了是否显示
     * 0 为状态栏，1 为 底部虚拟导航
     */
    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    fun isSystemUiVisible(window: Window): Array<Boolean> {
        val result = arrayOf(false, false)

        val attributes = window.getAttributes() as WindowManager.LayoutParams

        result[0] =
            attributes.flags and WindowManager.LayoutParams.FLAG_FULLSCREEN != WindowManager.LayoutParams.FLAG_FULLSCREEN;
        val decorView = window.getDecorView() as ViewGroup;
        result[1] =
            (attributes.systemUiVisibility or decorView.windowSystemUiVisibility and View.SYSTEM_UI_FLAG_HIDE_NAVIGATION == 0 &&
                    attributes.flags and WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS != 0);

        //
        val decorViewObj = window.decorView
        val clazz = decorViewObj.javaClass
        var mLastBottomInset = 0
        var mLastRightInset = 0
        var mLastLeftInset = 0
        try {
            val mLastBottomInsetField = clazz.getDeclaredField("mLastBottomInset")
            mLastBottomInsetField.isAccessible = true
            mLastBottomInset = mLastBottomInsetField.getInt(decorViewObj)
        } catch (e: Exception) {
            e.printStackTrace()
        }

        try {
            val mLastRightInsetField = clazz.getDeclaredField("mLastRightInset")
            mLastRightInsetField.isAccessible = true
            mLastRightInset = mLastRightInsetField.getInt(decorViewObj)
        } catch (e: Exception) {
            e.printStackTrace()
        }

        try {
            val mLastLeftInsetField = clazz.getDeclaredField("mLastLeftInset")
            mLastLeftInsetField.isAccessible = true
            mLastLeftInset = mLastLeftInsetField.getInt(decorViewObj)
        } catch (e: Exception) {
            e.printStackTrace()
        }

        val isNavBarToRightEdge = mLastBottomInset == 0 && mLastRightInset > 0
        val size =
            if (isNavBarToRightEdge) mLastRightInset else if (mLastBottomInset == 0 && mLastLeftInset > 0) mLastLeftInset else mLastBottomInset
        result[1] = result[1] && size > 0
        return result
    }
}
