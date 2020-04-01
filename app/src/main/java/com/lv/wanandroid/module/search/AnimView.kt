package com.lv.wanandroid.module.search

import android.view.View
import android.view.ViewGroup
import com.elvishew.xlog.XLog

/**
 * @name WanAndroid-kotlin
 * @class name：com.lv.wanandroid.module.search
 * @author 345 QQ:1831712732
 * @time 2020/4/1 21:09
 * @description
 */
class AnimView(val view: View) {

    fun setLeftMargin(left: Int) {
        val lf = view.layoutParams as ViewGroup.MarginLayoutParams
        lf.leftMargin = left
        view.layoutParams = lf
    }

    fun setRightMargin(right: Int) {
        val lr = view.layoutParams as ViewGroup.MarginLayoutParams
        lr.rightMargin = right
        view.layoutParams  = lr
    }
}