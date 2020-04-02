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

    fun requestArticle(block: (Pair<BaseArticle<MutableList<Article?>>, BaseArticle<ArticlePage?>>) -> Unit) {
        LvHttp.zip(Pair({
            LvHttp.get().addUrl("article/top/json").send()
        }, {
            LvHttp.get().addUrl("article/list/0/json").send()
        })) {
            block(Pair(format(it.first.value), format(it.second.value)))
        }
    }

     private inline fun <reified T> format(value: String): T {
         return Gson().fromJson<T>(value, object : TypeToken<T>() {}.type)
     }

    fun requestArticlePage(page: Int, block: (BaseArticle<ArticlePage?>) -> Unit) {
        LvHttp.get("article/list/${page}/json").send {
            block(format(it.value))
        }
    }


}