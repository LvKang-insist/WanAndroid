package com.lv.wanandroid.module.system.bean

data class TreeBean(
    val data: List<Data>,
    val errorCode: Int, // 0
    val errorMsg: String
) {
    data class Data(
        val children: List<Children>,
        val courseId: Int, // 13
        val id: Int, // 511
        val name: String, // 设计Tab
        val order: Int, // 245
        val parentChapterId: Int, // 0
        val userControlSetTop: Boolean, // false
        val visible: Int // 1
    ) {
        data class Children(
            val children: List<Any>,
            val courseId: Int, // 13
            val id: Int, // 525
            val name: String, // 压缩网站
            val order: Int, // 245013
            val parentChapterId: Int, // 511
            val userControlSetTop: Boolean, // false
            val visible: Int // 1
        )
    }
}