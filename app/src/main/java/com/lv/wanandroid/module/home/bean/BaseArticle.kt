package com.lv.wanandroid.module.home.bean

/**
 * @name WanAndroid-kotlin
 * @class name：com.lv.wanandroid.bean
 * @author 345 QQ:1831712732
 * @time 2020/3/26 0:13
 * @description 集成数据
 */

data class BaseArticle<T>(
    var data: T?,
    var results: T?,
    val errorMsg: String? = null,
    var errorCode: Int? = -1,
    var error: Boolean? = true
)
