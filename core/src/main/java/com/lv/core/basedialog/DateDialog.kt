package com.lv.core.basedialog

import android.view.View
import android.widget.TextView
import android.widget.Toast
import com.lv.core.R

/**
 * @author 345
 * Created by Administrator on 2019/10/7.
 */
class DateDialog(
    view: Any?,
    alpha: Float,
    autoDismiss: Boolean,
    cancelable: Boolean,
    animation: Int,
    gravity: Int
) : BaseFragDialog(view, alpha, autoDismiss, cancelable, animation, gravity),
    View.OnClickListener {
    private var tv1: TextView? = null
    private var tv2: TextView? = null
    override fun initView(view: View?) {
        tv1 = view!!.findViewById(R.id.tv_dialog_cancel)
        tv2 = view.findViewById(R.id.tv_dialog_confirm)
        tv1?.setOnClickListener(this)
        tv2?.setOnClickListener(this)
        val title = view.findViewById<TextView>(R.id.tv_dialog_title)
        val message = view.findViewById<TextView>(R.id.tv_dialog_message)
        title.text = "我是日期对话框"
        message.text = "我是时间戳：" + System.currentTimeMillis()
    }

    override fun onClick(view: View) {
        val i = view.id
        if (i == R.id.tv_dialog_cancel) {
            Toast.makeText(context, "取消", Toast.LENGTH_SHORT).show()
            dismiss()
        } else if (i == R.id.tv_dialog_confirm) {
            Toast.makeText(context, "成功", Toast.LENGTH_SHORT).show()
            dismiss()
        }
    }

    companion object {
        /**
         * 继承的类必须写此方法，泛型为当前类
         */
        fun DateBuilder(): DialogBuilder<DateDialog> {
            return DialogBuilder(DateDialog::class.java)
        }
    }
}