package com.lv.wanandroid.module.project.bean

data class Nav(
    val data: List<Data>,
    val errorCode: Int,
    val errorMsg: String
)

data class Data(
    val children: List<Any>,
    val courseId: Int,
    val id: Int,
    val name: String,
    val order: Int,
    val parentChapterId: Int,
    val userControlSetTop: Boolean,
    val visible: Int
)
//{
//    "children": [],
//    "courseId": 13,
//    "id": 294,
//    "name": "完整项目",
//    "order": 145000,
//    "parentChapterId": 293,
//    "userControlSetTop": false,
//    "visible": 0
//}