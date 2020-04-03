package com.lv.wanandroid.nav.login.bean

/**
 * @name WanAndroid-kotlin
 * @class name：com.lv.wanandroid.login.bean
 * @author 345 QQ:1831712732
 * @time 2020/4/2 22:04
 * @description
 */

data class LogBean(
    val data: Data,
    val errorCode: Int, // 0
    val errorMsg: String
) {
    data class Data(
        val admin: Boolean, // false
        val chapterTops: List<Any>,
        val collectIds: List<Any>,
        val email: String,
        val icon: String,
        val id: Int, // 56550
        val nickname: String, // 345
        val password: String,
        val publicName: String, // 345
        val token: String,
        val type: Int, // 0
        val username: String // 345
    )
}