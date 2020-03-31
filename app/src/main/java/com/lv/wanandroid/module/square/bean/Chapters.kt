package com.lv.wanandroid.module.square.bean

data class Chapters(
    val data: List<Data>,
    val errorCode: Int, // 0
    val errorMsg: String
) {
    data class Data(
        val children: List<Any>,
        val courseId: Int, // 13
        val id: Int, // 408
        val name: String, // 鸿洋
        val order: Int, // 190000
        val parentChapterId: Int, // 407
        val userControlSetTop: Boolean, // false
        val visible: Int // 1
    )
}