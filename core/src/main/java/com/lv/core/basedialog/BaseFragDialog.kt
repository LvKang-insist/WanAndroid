package com.lv.core.basedialog

import android.R
import android.app.Activity
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.SparseArray
import android.view.*
import android.widget.ImageView
import androidx.annotation.IdRes
import androidx.appcompat.widget.AppCompatButton
import androidx.appcompat.widget.AppCompatTextView
import androidx.fragment.app.DialogFragment
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.lv.core.utils.ToolsUtils.getScreenWidth

/**
 * @author 345
 * Created by Administrator on 2019/10/8.
 */
open class BaseFragDialog internal constructor(
    private val mView: Any?,
    /**
     * 透明度
     */
    private val mAlpha: Float,
    /**
     * 是否开启关闭事件
     */
    private val mAutoDismiss: Boolean,
    /**
     * 点击对话框外是否关闭对话框
     */
    private val mCancelable: Boolean,
    /**
     * 动画
     */
    private val mAnimation: Int,
    /**
     * 位置
     */
    private val mGravity: Int
) : DialogFragment() {

    private var window: Window? = null
    private var mActivity: Activity? = null
    /**
     * 根布局
     */
    private var mRootView: View? = null
    /**
     * 宽度比
     */
    private var mWidthPercent = -1f
    private var mClickArray: SparseArray<OnListener>?
    private var mSetText: SparseArray<String>?
    private val mSetImage: SparseArray<String>

    private var handler: Handler? = Handler(Looper.getMainLooper())
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        window = dialog!!.window
        if (window != null) {
            if (mView is Int) {
                mRootView = inflater.inflate(
                    (mView as Int?)!!,
                    window!!.findViewById<View>(R.id.content) as ViewGroup,
                    false
                )
            } else if (mView is View) {
                mRootView = mView
            } else {
                throw NullPointerException("Not Layout File ")
            }
            create()
        }
        return mRootView
    }

    protected open fun setWindow(window: Window?) {}
    /**
     * 设置背景遮盖层开关
     */
    fun setBackgroundDimEnabled(enabled: Boolean) {
        if (window != null) {
            if (enabled) {
                window!!.addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND)
            } else {
                window!!.clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND)
            }
        }
    }

    /**
     * 设置背景遮盖层的透明度（前提条件是背景遮盖层开关必须是为开启状态）
     */
    fun setBackgroundDimAmount(dimAmount: Float) {
        if (window != null) {
            window!!.setDimAmount(dimAmount)
        }
    }

    /**
     * 设置 文本
     *
     * @param id      id
     * @param strings 内容
     */
    fun setText(@IdRes id: Int, strings: String): BaseFragDialog {
        mSetText!!.put(id, strings)
        return this
    }

    /**
     * 设置 图片
     *
     * @param id  id
     * @param url 内容
     */
    fun setImageUrl(@IdRes id: Int, url: String): BaseFragDialog {
        mSetImage.put(id, url)
        return this
    }

    /**
     * 监听事件
     */
    fun setListener(id: Int, listener: OnListener): BaseFragDialog {
        mClickArray!!.put(id, listener)
        return this
    }

    /**
     * 设置dialog 宽度，如果在布局中指定了宽度，这里可不用设置。
     * 注意：这里是相对于屏幕的百分比，而不是直接设置宽度
     *
     * @param activity     activity
     * @param widthPercent 百分比宽度：0.1 - 1
     * @return this
     */
    fun setWidth(activity: Activity?, widthPercent: Float): BaseFragDialog {
        mWidthPercent = widthPercent
        mActivity = activity
        return this
    }

    private fun setLocation() {
        val attributes = window!!.attributes
        if (mActivity != null && mWidthPercent >= 0) {
            attributes.width = (getScreenWidth(mActivity!!) * mWidthPercent).toInt()
        }
        attributes.alpha = mAlpha
        attributes.gravity = mGravity
        window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        window!!.attributes = attributes
    }

    /**
     * 延时发送，在指定的时间执行
     */
    fun postAtTime(uptimeMillis: Long, run: Runnable) {
        handler?.postDelayed(run, uptimeMillis)
    }

    /**
     * 空实现，如果dialog的逻辑过于复杂，则可以继承此类，实现此方法。
     * 这个方法可用于绑定 view 进行一些初始化等操作
     */
    open fun initView(view: View?) {}

    private fun create() {
        setLocation()
        setWindow(window)
        initView(mRootView)
        isCancelable = mCancelable
        window!!.setWindowAnimations(mAnimation)
        for (i in 0 until mSetText!!.size()) {
            val viewById =
                mRootView!!.findViewById<View>(mSetText!!.keyAt(i))
            if (viewById is AppCompatTextView) {
                viewById.text = mSetText!!.valueAt(i)
            } else if (viewById is AppCompatButton) {
                viewById.text = mSetText!!.valueAt(i)
            }
        }
        if (!mAutoDismiss) {
            for (i in 0 until mClickArray!!.size()) {
                mRootView!!.findViewById<View>(mClickArray!!.keyAt(i))
                    .setOnClickListener(ViewOnClick(this, mClickArray!!.valueAt(i)))
            }
        }
        for (i in 0 until mSetImage.size()) {
            val image =
                mRootView!!.findViewById<ImageView>(mSetImage.keyAt(i))
            Glide.with(this)
                .load(mSetImage.valueAt(i))
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(image)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        if (mSetText != null) {
            mSetText!!.clear()
            mSetText = null
        }
        if (mClickArray != null) {
            mClickArray!!.clear()
            mClickArray = null
        }
        if (mRootView != null) {
            mRootView = null
        }
        if (mActivity != null) {
            mActivity = null
        }
        if (handler != null) {
            handler?.removeCallbacksAndMessages(null)
            handler = null
        }
    }

    companion object {

        fun newInstance(
            view: Any?, alpha: Float,
            mAutoDismiss: Boolean, cancelable: Boolean,
            animation: Int, gravity: Int
        ): BaseFragDialog {
            return BaseFragDialog(view, alpha, mAutoDismiss, cancelable, animation, gravity)
        }

        fun Builder(): DialogBuilder<BaseFragDialog> {
            return DialogBuilder()
        }
    }

    init {
        mClickArray = SparseArray()
        mSetText = SparseArray()
        mSetImage = SparseArray()
    }
}