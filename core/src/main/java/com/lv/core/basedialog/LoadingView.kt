package com.lv.core.basedialog

import android.view.Gravity
import androidx.fragment.app.FragmentManager
import com.lv.core.R
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.lang.ref.WeakReference

/**
 * @author 345 QQ:1831712732
 * @name CarSteward
 * @class name：com.car.ui.dialog
 * @time 2019/11/26 21:14
 * @description 全局加载对话框
 */
object LoadingView {

    var mdialog: WeakReference<ToastDialog>? = null

    /**
     * 初始化 loading
     *
     * @param msg 消息
     */
    private fun createLoadingDialog(msg: String) {

        var message: String? = msg
        if (message == null) {
            message = "正在加载..."
        }
        mdialog = WeakReference(
            ToastDialog.Companion.ToastBuilder()
                .setContentView(R.layout.dialog_toast)
                .setGravity(Gravity.CENTER)
                .build()
                .setMessage(message)
        )
    }

    /**
     * 关闭 Loading
     */
    fun stopLoading() {
        mdialog?.get()?.dismiss()
    }

    /**
     * 延时关闭，毫秒为单位
     */
    fun stopLoading(time: Long) {
        GlobalScope.launch(Dispatchers.IO) {
            delay(time)
            launch(Dispatchers.Main) {
                stopLoading()
            }
        }
    }

    /**
     * 显示加载对话框
     *
     * @param msg 若为 null ，显示正在加载，否则显示 msg
     */
    fun showLoading(
        msg: String,
        manager: FragmentManager?
    ) {
        createLoadingDialog(msg)
        if (!mdialog?.get()!!.isVisible) {
            mdialog?.get()!!
                .setType(ToastDialog.Type.LOADING)
                .show(manager!!, "latte")
            stopLoading(3000)
        }
    }
}