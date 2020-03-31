package com.lv.wanandroid.module.square.bean

/**
 * @name WanAndroid-kotlin
 * @class name：com.lv.wanandroid.module.square.bean
 * @author 345 QQ:1831712732
 * @time 2020/3/31 22:23
 * @description
 */
data class Wxarticle(
    val data: Data,
    val errorCode: Int, // 0
    val errorMsg: String
) {
    data class Data(
        val curPage: Int, // 0
        val datas: List<Data>,
        val offset: Int, // -20
        val over: Boolean, // false
        val pageCount: Int, // 45
        val size: Int, // 20
        val total: Int // 897
    ) {
        data class Data(
            val apkLink: String,
            val audit: Int, // 1
            val author: String, // 鸿洋
            val canEdit: Boolean, // false
            val chapterId: Int, // 408
            val chapterName: String, // 鸿洋
            val collect: Boolean, // false
            val courseId: Int, // 13
            val desc: String,
            val descMd: String,
            val envelopePic: String,
            val fresh: Boolean, // false
            val id: Int, // 12094
            val link: String, // https://mp.weixin.qq.com/s/zx439XBaZJneXZAhlF2mEQ
            val niceDate: String, // 2020-02-21 00:00
            val niceShareDate: String, // 2020-02-26 19:58
            val origin: String,
            val prefix: String,
            val projectLink: String,
            val publishTime: Long, // 1582214400000
            val selfVisible: Int, // 0
            val shareDate: Long, // 1582718287000
            val shareUser: String,
            val superChapterId: Int, // 408
            val superChapterName: String, // 公众号
            val tags: List<Any>,
            val title: String, // ViewStub你肯定听过，但是这些细节了解吗？
            val type: Int, // 0
            val userId: Int, // -1
            val visible: Int, // 1
            val zan: Int // 0
        )
    }
}