package com.lv.core.utils.storage

import android.content.SharedPreferences
import com.lv.core.utils.AppContext.Companion.getApplication

/**
 * Copyright (C)
 *
 * @file: LattePreference
 * @author: 345
 * @Time: 2019/4/21 11:25
 * @description:
 */
object PreferenceUtils {
    private const val USER = "user"
    private const val DEFAULT = "default"

    private fun getAppPreferenceEdit(s: String?): SharedPreferences.Editor {
        return getApplication().getSharedPreferences(s, 0)
            .edit()
    }

    private fun getAppPreference(s: String?): SharedPreferences {
        return getApplication().getSharedPreferences(s, 0)
    }


    fun setValue(key: String?, string: String?) {
        getAppPreferenceEdit(DEFAULT)
            .putString(key, string)
            .apply()
    }

    fun getValue(key: String?): String? {
        return getAppPreference(DEFAULT).getString(key, "")
    }


    /**
     * 保存是否登陆
     *
     * @param isLongin true 为登录
     */
    fun putLogin(isLongin: Boolean?) {
        getAppPreferenceEdit(USER)
            .putBoolean("isLogin", isLongin!!)
            .commit()
    }

    /**
     * 获取是否登陆
     *
     * @return true 表示登录
     */
    val login: Boolean
        get() = getAppPreference(USER).getBoolean("isLogin", false)


    /**
     * 保存用户昵称
     *
     * @param userName 昵称
     */
    fun putUserName(userName: String?) {
        getAppPreferenceEdit(USER)
            .putString("userName", userName)
            .commit()
    }

    /**
     * 获取用户 昵称
     *
     * @return 用户昵称
     */
    val userName: String?
        get() = getAppPreference(USER).getString("userName", null)


    /**
     * 保存用户ID
     *
     * @param userName ID
     */
    fun putUserId(userName: Int) {
        getAppPreferenceEdit(USER)
            .putInt("userId", userName)
            .commit()
    }

    /**
     * 获取用户 ID
     *
     * @return 用户ID
     */
    val userId: Int
        get() = getAppPreference(USER).getInt("userId", 0)


    /**
     * 保存 头像
     *
     * @param userPhoto 头像地址
     */
    fun putUserPhoto(userPhoto: String?) {
        var userUrl = userPhoto
        if (userUrl == null) {
            userUrl = ""
        }
        getAppPreferenceEdit(USER)
            .putString("userPhoto", userUrl)
            .commit()
    }

    /**
     * 获取用户头像
     *
     * @return 头像地址
     */
    val userPhoto: String?
        get() = getAppPreference(USER).getString("userPhoto", null)


    /**
     * 保存 PassWordcookie
     */
    fun putCookiePass(cookiePass: String?) {
        getAppPreferenceEdit(USER)
            .putString("cookiePass", cookiePass)
            .commit()
    }

    /**
     * 获取 PassWordcookie
     */
    val cookiePass: String?
        get() {
            val cookie =
                getAppPreference(USER).getString("cookiePass", "")
            return if (cookie == null || cookie.isEmpty()) {
                null
            } else {
                cookie
            }
        }

    /**
     * 保存 nameCookie
     *
     * @param cookieName tokenId
     */
    fun putCookieLoginUserName(cookieName: String?) {
        getAppPreferenceEdit(USER).putString("CookieName", cookieName)
            .commit()
    }

    /**
     * 获取 nameCookie
     *
     * @return 返回 tokenid，
     */
    val cookieLoginUserName: String?
        get() = getAppPreference(USER).getString("CookieName", null)


}