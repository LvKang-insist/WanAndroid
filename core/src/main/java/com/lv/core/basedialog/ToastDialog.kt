package com.lv.core.basedialog

import android.view.View
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.AppCompatTextView
import com.lv.core.R

/**
 * Created by Administrator on 2019/10/9.
 */
class ToastDialog(
    view: Any?,
    alpha: Float,
    autoDismiss: Boolean,
    cancelable: Boolean,
    animation: Int,
    gravity: Int
) : BaseFragDialog(view, alpha, autoDismiss, cancelable, animation, gravity) {
    private var mType: Type? = null
    private var mMessage: String? = null
    private var icon: AppCompatImageView? = null
    private var message: AppCompatTextView? = null
    private var mUptimeMillis: Long = 0
    private var progressView: ProgressView? = null
    override fun initView(view: View?) {
        icon = view!!.findViewById(R.id.iv_toast_icon)
        message = view.findViewById(R.id.tv_toast_message)
        progressView = view.findViewById(R.id.pw_progress)
        if (mType == null) {
            throw NullPointerException("没有设置类型，请调用 setType() 设置类型")
        }
        crate()
        //取消背景遮罩
        setBackgroundDimEnabled(false)
    }

    private fun crate() {
        icon!!.visibility = View.VISIBLE
        progressView!!.visibility = View.GONE
        when (mType) {
            Type.FINISH -> icon!!.setImageResource(R.drawable.ic_dialog_finish)
            Type.ERROR -> icon!!.setImageResource(R.drawable.ic_dialog_error)
            Type.WARN -> icon!!.setImageResource(R.drawable.ic_dialog_warning)
            Type.LOADING -> {
                icon!!.visibility = View.GONE
                progressView!!.visibility = View.VISIBLE
            }
            else -> {
            }
        }
        if (mMessage == null) {
            message!!.visibility = View.GONE
        } else {
            message!!.visibility = View.VISIBLE
            message!!.text = mMessage
        }
        if (mUptimeMillis > 0) {
            postAtTime(mUptimeMillis, Runnable { dismiss() })
        }
    }

    /**
     * 设置显示类型
     *
     * @param type 类型
     */
    fun setType(type: Type?): ToastDialog {
        mType = type
        return this
    }

    /**
     * 设置消息内容
     */
    fun setMessage(message: String?): ToastDialog {
        mMessage = message
        return this
    }

    /**
     * 延时关闭
     *
     * @param uptimeMillis 时间，毫秒为单位
     */
    fun postAtTime(uptimeMillis: Long): ToastDialog {
        mUptimeMillis = uptimeMillis
        return this
    }

    /**
     * 显示的类型
     */
    enum class Type {
        // 完成，错误，警告,  加载中
        FINISH,
        ERROR, WARN, LOADING
    }

    companion object {
        fun ToastBuilder(): DialogBuilder<ToastDialog> {
            return DialogBuilder(ToastDialog::class.java)
        }
    }
}