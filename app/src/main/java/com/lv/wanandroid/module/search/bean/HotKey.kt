package com.lv.wanandroid.module.search.bean

data class HotKey(
    val data: List<Data>,
    val errorCode: Int, // 0
    val errorMsg: String
) {
    data class Data(
        val id: Int, // 6
        val link: String,
        val name: String, // 面试
        val order: Int, // 1
        val visible: Int // 1
    )
}