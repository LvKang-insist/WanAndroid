package com.lv.wanandroid.module.home.mvp

import com.hjq.toast.ToastUtils
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
            if (it.first != null && it.second != null) {
                block(
                    Pair(
                        it.first!!.format(it.first!!.value),
                        it.second!!.format(it.second!!.value)
                    )
                )
            } else {
                ToastUtils.show("网络错误")
            }
        }
    }

    fun requestArticlePage(page: Int, block: (BaseArticle<ArticlePage?>) -> Unit) {
        LvHttp.get("article/list/${page}/json").send {
            block(it.format(it.value))
        }
    }
}