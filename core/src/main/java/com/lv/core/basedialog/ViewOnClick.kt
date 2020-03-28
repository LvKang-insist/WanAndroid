package com.lv.core.basedialog

import android.view.View

/**
 * @author 345 QQ:1831712732
 * @name Android Business Toos
 * @class name：com.business.tools.basedialog.utils
 * @time 2019/12/23 21:55
 * @description 对点击事件进行处理
 */
class ViewOnClick internal constructor(
    private val dialog: BaseFragDialog,
    private val listener: OnListener
) :
    View.OnClickListener {
    override fun onClick(view: View) {
        listener.onClick(dialog, view)
    }

}