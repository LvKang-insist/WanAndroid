package com.lv.wanandroid.module.home.bean

import com.lv.wanandroid.module.home.bean.Article

/**
 * @name WanAndroid-kotlin
 * @class name：com.lv.wanandroid.bean
 * @author 345 QQ:1831712732
 * @time 2020/3/25 23:52
 * @description 分页数据
 */

data class ArticlePage(
    val pageCount: Int,
    val curPage: Int,
    val datas: List<Article?>?
)
