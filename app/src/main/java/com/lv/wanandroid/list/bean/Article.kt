package com.lv.wanandroid.list.bean

data class Article(
    val data: Data,
    val errorCode: Int, // 0
    val errorMsg: String
) {
    data class Data(
        val curPage: Int, // 1
        val datas: List<Data>,
        val offset: Int, // 0
        val over: Boolean, // false
        val pageCount: Int, // 3
        val size: Int, // 20
        val total: Int // 51
    ) {
        data class Data(
            val apkLink: String,
            val audit: Int, // 1
            val author: String,
            val canEdit: Boolean, // false
            val chapterId: Int, // 60
            val chapterName: String, // Android Studio相关
            val collect: Boolean, // false
            val courseId: Int, // 13
            val desc: String,
            val descMd: String,
            val envelopePic: String,
            val fresh: Boolean, // false
            val id: Int, // 12470
            val link: String, // https://juejin.im/post/5e7194b6e51d4526cd1e0903
            val niceDate: String, // 2020-03-19 00:39
            val niceShareDate: String, // 2020-03-18 16:56
            val origin: String,
            val prefix: String,
            val projectLink: String,
            val publishTime: Long, // 1584549582000
            val selfVisible: Int, // 0
            val shareDate: Long, // 1584521810000
            val shareUser: String, // goweii
            val superChapterId: Int, // 60
            val superChapterName: String, // 开发环境
            val tags: List<Any>,
            val title: String, // Android 11 中的存储机制更新
            val type: Int, // 0
            val userId: Int, // 20382
            val visible: Int, // 1
            val zan: Int // 0
        )
    }
}