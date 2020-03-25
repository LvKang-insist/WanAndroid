package com.lv.wanandroid.module.home.mvp

import com.elvishew.xlog.XLog
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.lv.core.mvp.IContract
import com.lv.wanandroid.module.home.bean.Article
import com.lv.wanandroid.module.home.bean.ArticlePage
import com.lv.wanandroid.module.home.bean.BaseArticle
import com.www.net.LvHttp

/**
 * @name WanAndroid-kotlin
 * @class name：com.lv.wanandroid.module.home.mvp
 * @author 345 QQ:1831712732
 * @time 2020/3/25 20:40
 * @description
 */

class HomeModel : IContract.BaseModel() {

    fun requestArticle(block: (Pair<BaseArticle<List<Article?>>, BaseArticle<ArticlePage?>>) -> Unit) {
        LvHttp.get().pair({
            val article = it.addUrl("article/top/json").send()
            val a1 = Gson().fromJson<BaseArticle<List<Article?>>>(
                article.value, object : TypeToken<BaseArticle<List<Article?>>>() {}.type
            )
            XLog.e("*************" + article.value)
            val articlePage = it.addUrl("article/list/0/json")
                .send()
            val a2 = Gson().fromJson<BaseArticle<ArticlePage?>>(
                articlePage.value,
                object : TypeToken<BaseArticle<ArticlePage?>>() {}.type
            )
            XLog.e("*************" + articlePage.value)
            Pair(a1, a2)
        }) {
            block(it)
        }
    }


}