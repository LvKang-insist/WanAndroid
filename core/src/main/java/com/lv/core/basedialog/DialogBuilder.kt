package com.lv.core.basedialog

import androidx.annotation.StyleRes

/**
 * @author 345
 * @date 2019/10/7
 */
class DialogBuilder<T> {
    private var view: Any? = null
    private var mAlpha = 1f
    private var mAutoDismiss = false
    private var mCancelable = true
    private var mAnimation = 0
    private var mGravity = 0
    var tClass: Class<T>? = null

    constructor() {}
    constructor(tClass: Class<T>?) {
        this.tClass = tClass
    }

    /**
     * 设置布局资源，可以为 ID，也可以是 View
     */
    fun setContentView(view: Any?): DialogBuilder<T> {
        this.view = view
        return this
    }

    /**
     * 设置透明度透明度
     *
     * @param alpha 从 0 - 1
     */
    fun setAlpha(alpha: Float): DialogBuilder<T> {
        mAlpha = alpha
        return this
    }

    /**
     * 若为 true 所有的点击事件都不起作用，否则相反
     */
    fun setAutoDismiss(autoDismiss: Boolean): DialogBuilder<T> {
        mAutoDismiss = autoDismiss
        return this
    }

    /**
     * 若为 false，对话框不可取消
     */
    fun setCancelable(cancelable: Boolean): DialogBuilder<T> {
        mCancelable = cancelable
        return this
    }

    /**
     * 设置动画
     */
    fun setAnimation(@StyleRes animation: Int): DialogBuilder<T> {
        mAnimation = animation
        return this
    }

    /**
     * 设置对话框位置
     */
    fun setGravity(gravity: Int): DialogBuilder<T> {
        mGravity = gravity
        return this
    }

    /**
     * @return 对话框的实例
     */
    fun build(): T {
        return if (tClass != null) {
            try {
                val constructor = tClass?.getConstructor(
                    Any::class.java,
                    Float::class.javaPrimitiveType,
                    Boolean::class.javaPrimitiveType,
                    Boolean::class.javaPrimitiveType,
                    Int::class.javaPrimitiveType,
                    Int::class.javaPrimitiveType
                )
                constructor!!.newInstance(
                    view,
                    mAlpha,
                    mAutoDismiss,
                    mCancelable,
                    mAnimation,
                    mGravity
                )
            } catch (e: Exception) {
                throw RuntimeException("创建 " + javaClass.name + " 失败，原因可能是构造参数有问题：" + e.message)
            }
        } else {
            BaseFragDialog.Companion.newInstance(
                view,
                mAlpha,
                mAutoDismiss,
                mCancelable,
                mAnimation,
                mGravity
            ) as T
        }
    }
}