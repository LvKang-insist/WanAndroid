package com.lv.wanandroid.module.search

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Color
import android.view.LayoutInflater
import androidx.appcompat.widget.AppCompatTextView
import com.lv.wanandroid.R
import com.lv.wanandroid.base.BaseActivity
import com.lv.wanandroid.list.ListActivity
import com.lv.wanandroid.module.search.bean.HotKey
import com.lv.wanandroid.module.search.mvp.SearchContract
import com.lv.wanandroid.module.search.mvp.SearchPresenter
import kotlinx.android.synthetic.main.activity_search.*
import java.util.*

class SearchActivity : BaseActivity<SearchContract.View, SearchContract.Presenter>(),
    SearchContract.View {


    override fun createPresenter(): SearchContract.Presenter {
        return SearchPresenter()
    }

    override fun layoutId(): Int {
        return R.layout.activity_search
    }

    override fun bindView() {
        mPresenter.requestHotkey()
    }

    override fun resultHotKey(hotKey: List<HotKey.Data>) {
        hotKey.forEach {
            val text = LayoutInflater.from(this)
                .inflate(
                    R.layout.search_tv_item, activity_search_popular, false
                ) as AppCompatTextView
            text.text = it.name
            text.setTextColor(Color.parseColor(getRandColorCode()))
            text.setOnClickListener {
                val intent = Intent(this, ListActivity::class.java)
                intent.putExtra(ListActivity.KEY, ListActivity.SEARCH_LIST)
                intent.putExtra("name", text.text!!)
               startActivity(intent)
            }
            activity_search_popular.addView(text)
        }

    }

    @SuppressLint("DefaultLocale")
    private fun getRandColorCode(): String {
        val random = Random();
        var r = Integer.toHexString(random.nextInt(256)).toUpperCase();
        var g = Integer.toHexString(random.nextInt(256)).toUpperCase();
        var b = Integer.toHexString(random.nextInt(256)).toUpperCase();

        if (r.length == 1) r = "0$r"
        if (g.length == 1) g = "0$g"
        if (b.length == 1) b = "0$b"

        return "#${r + g + b}"
    }

}