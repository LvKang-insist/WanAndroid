package com.lv.wanandroid.receiver

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import com.lv.wanandroid.web.AgentWebActivity
import com.xiaomi.mipush.sdk.*


class MiMessageReceiver : PushMessageReceiver() {

    private var mRegId: String? = null
    private val mResultCode: Long = -1
    private val mReason: String? = null
    private val mCommand: String? = null
    private var mMessage: String? = null
    private var mTopic: String? = null
    private var mAlias: String? = null
    private var mUserAccount: String? = null
    private var mStartTime: String? = null
    private var mEndTime: String? = null

    /**
     * 接收服务器发送的透传消息
     */
    override fun onReceivePassThroughMessage(context: Context, message: MiPushMessage) {
        mMessage = message.content
        if (!TextUtils.isEmpty(message.topic)) {
            mTopic = message.topic
        } else if (!TextUtils.isEmpty(message.alias)) {
            mAlias = message.alias
        } else if (!TextUtils.isEmpty(message.userAccount)) {
            mUserAccount = message.userAccount
        }
    }

    /**
     * 接收服务器发来的通知栏消息(用户点击时触发)
     */
    override fun onNotificationMessageClicked(context: Context, message: MiPushMessage) {
        mMessage = message.content
        if (!TextUtils.isEmpty(message.topic)) {
            mTopic = message.topic
        } else if (!TextUtils.isEmpty(message.alias)) {
            mAlias = message.alias
        } else if (!TextUtils.isEmpty(message.userAccount)) {
            mUserAccount = message.userAccount
        }

        val intent = Intent(); //创建Intent对象
        val componentName =
            ComponentName("com.lv.wanandroid", "com.lv.wanandroid.web.AgentWebActivity");
        intent.component = componentName;//调用Intent的setComponent()方法实现传递
        val bundle = Bundle()
        bundle.putString("link", message.content)
        intent.putExtras(bundle)
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP)
        context.startActivity(intent)
    }

    /**
     * 接收服务器发来的通知栏消息(消息到达后触发，并且可以接收应用在前台时不弹出通知的通知信息)
     */
    override fun onNotificationMessageArrived(context: Context, message: MiPushMessage) {
        mMessage = message.content
        if (!TextUtils.isEmpty(message.topic)) {
            mTopic = message.topic
        } else if (!TextUtils.isEmpty(message.alias)) {
            mAlias = message.alias
        } else if (!TextUtils.isEmpty(message.userAccount)) {
            mUserAccount = message.userAccount
        }
        val intent = Intent(context, AgentWebActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP
        intent.putExtra("link", message.content)
        context.startActivity(intent)
    }

    /**
     * 用来接收客户端向服务端发送命令后返回的响应
     */
    override fun onCommandResult(context: Context, message: MiPushCommandMessage) {
        val command = message.command
        val arguments = message.commandArguments
        val cmdArg1 = if (arguments != null && arguments.size > 0) arguments[0] else null
        val cmdArg2 = if (arguments != null && arguments.size > 1) arguments[1] else null
        if (MiPushClient.COMMAND_REGISTER == command) {
            if ((message.resultCode).toInt() == ErrorCode.SUCCESS) {
                mRegId = cmdArg1
            }
        } else if (MiPushClient.COMMAND_SET_ALIAS == command) {
            if ((message.resultCode).toInt() == ErrorCode.SUCCESS) {
                mAlias = cmdArg1
            }
        } else if (MiPushClient.COMMAND_UNSET_ALIAS == command) {
            if ((message.resultCode).toInt() == ErrorCode.SUCCESS) {
                mAlias = cmdArg1
            }
        } else if (MiPushClient.COMMAND_SUBSCRIBE_TOPIC == command) {
            if ((message.resultCode).toInt() == ErrorCode.SUCCESS) {
                mTopic = cmdArg1
            }
        } else if (MiPushClient.COMMAND_UNSUBSCRIBE_TOPIC == command) {
            if ((message.resultCode).toInt() == ErrorCode.SUCCESS) {
                mTopic = cmdArg1
            }
        } else if (MiPushClient.COMMAND_SET_ACCEPT_TIME == command) {
            if ((message.resultCode).toInt() == ErrorCode.SUCCESS) {
                mStartTime = cmdArg1
                mEndTime = cmdArg2
            }
        }
    }

    /**
     * 用来接收客户端向服务器发送注册命令后返回的响应
     */
    override fun onReceiveRegisterResult(context: Context, message: MiPushCommandMessage) {
        val command = message.command
        val arguments = message.commandArguments
        val cmdArg1 = if (arguments != null && arguments.size > 0) arguments[0] else null
        val cmdArg2 = if (arguments != null && arguments.size > 1) arguments[1] else null
        if (MiPushClient.COMMAND_REGISTER == command) {
            if ((message.resultCode).toInt() == ErrorCode.SUCCESS) {
                mRegId = cmdArg1
            }
        }
    }

}