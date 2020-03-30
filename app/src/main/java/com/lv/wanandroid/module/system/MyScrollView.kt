package com.lv.wanandroid.module.system

import android.content.Context
import android.util.AttributeSet
import android.view.MotionEvent
import androidx.core.widget.NestedScrollView

class MyScrollView : NestedScrollView {
    constructor(context: Context) : this(context, null)

    constructor(context: Context, attributeSet: AttributeSet?) : this(context, attributeSet, 1)

    constructor(context: Context, attributeSet: AttributeSet?, defStyleAttr: Int) : super(
        context, attributeSet, defStyleAttr
    )

    override fun dispatchTouchEvent(ev: MotionEvent?): Boolean {
        if (ev != null) {
            when (ev.action) {
                MotionEvent.ACTION_DOWN -> {
                    //父容器禁止拦截
                    parent.requestDisallowInterceptTouchEvent(true);
                }
                MotionEvent.ACTION_MOVE -> {
//                    if (事件交给父容器的条件) {
//                        parent.requestDisallowInterceptTouchEvent(false);
//                    }
                }
                MotionEvent.ACTION_UP -> {
                }
            }
        }

//        https://blog.csdn.net/colinandroid/article/details/72770863?depth_1-utm_source=distribute.pc_relevant.none-task&utm_source=distribute.pc_relevant.none-task

        return super.dispatchTouchEvent(ev)
    }

}