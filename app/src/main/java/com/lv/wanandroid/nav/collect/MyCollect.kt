package com.lv.wanandroid.nav.collect

import android.app.Activity
import android.view.Gravity
import android.view.View
import androidx.fragment.app.FragmentManager
import com.elvishew.xlog.XLog
import com.hjq.toast.ToastUtils
import com.lv.core.basedialog.BaseFragDialog
import com.lv.core.basedialog.OnListener
import com.lv.core.utils.storage.PreferenceUtils
import com.lv.wanandroid.R

/**
 * 收藏对话框，调用 start 方法进行收藏
 */
class MyCollect(val id: Int, val message: String) {

    fun start(manager: FragmentManager, activity: Activity) {
        BaseFragDialog.Builder()
            .setAnimation(R.style.BottomAnimStyle)
            .setGravity(Gravity.CENTER)
            .setContentView(R.layout.dialog_hint)
            .build()
            .setWidth(activity, 0.7f)
            .setText(R.id.tv_dialog_title, "是否收藏")
            .setText(R.id.tv_dialog_message, message)
            .setListener(R.id.tv_dialog_cancel, object : OnListener {
                override fun onClick(dialog: BaseFragDialog?, view: View?) {
                    dialog?.dismiss()
                }
            })
            .setListener(R.id.tv_dialog_confirm, object : OnListener {
                override fun onClick(dialog: BaseFragDialog?, view: View?) {
                    if (PreferenceUtils.login) {
                        CollectModel().postCookie(
                            "lg/collect/$id/json", mutableMapOf(),
                            mutableMapOf(Pair("Cookie", PreferenceUtils.cookie))
                        ) { flag, it ->
                            if (flag) {
                                ToastUtils.show("收藏成功")
                            }
                            XLog.json(it.value)
                        }
                    } else {
                        ToastUtils.show("请进行登录")
                    }
                    dialog?.dismiss()
                }
            })
            .show(manager, "home")
    }
}