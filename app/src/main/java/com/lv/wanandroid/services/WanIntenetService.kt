package com.lv.wanandroid.services

import android.content.Context
import android.util.Log
import com.igexin.sdk.GTIntentService
import com.igexin.sdk.message.GTCmdMessage
import com.igexin.sdk.message.GTNotificationMessage
import com.igexin.sdk.message.GTTransmitMessage

/**
 * @name WanAndroid-kotlin
 * @class name：com.lv.wanandroid.services
 * @author 345 QQ:1831712732
 * @time 2020/4/3 21:48
 * @description
 */

class WanIntenetService : GTIntentService() {


    override fun onReceiveServicePid(p0: Context?, p1: Int) {
    }

    // 处理透传消息
    override fun onReceiveMessageData(p0: Context?, p1: GTTransmitMessage?) {
    }

    // 通知到达，只有个推通道下发的通知会回调此方法
    override fun onNotificationMessageArrived(p0: Context?, p1: GTNotificationMessage?) {
    }

    // 通知到达，只有个推通道下发的通知会回调此方法
    override fun onNotificationMessageClicked(p0: Context?, p1: GTNotificationMessage?) {
    }

    // 各种事件处理回执
    override fun onReceiveCommandResult(p0: Context?, p1: GTCmdMessage?) {
    }

    // 接收 cid
    override fun onReceiveClientId(p0: Context?, clientid: String?) {
        Log.e(TAG, "onReceiveClientId -> clientid = $clientid");
    }

    // cid 离线上线通知
    override fun onReceiveOnlineState(p0: Context?, p1: Boolean) {
    }

}