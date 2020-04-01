package com.lv.wanandroid.module.search

import android.animation.AnimatorSet
import android.animation.IntEvaluator
import android.animation.ObjectAnimator
import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Color
import android.view.LayoutInflater
import androidx.appcompat.widget.AppCompatTextView
import com.hjq.toast.ToastUtils
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
        activity_serach_back.setOnClickListener { finish() }
        activity_search_search.setOnClickListener {
            startList(activity_search_edit.text.toString())
        }
        startAnimator()
    }



    private fun startAnimator() {
        val animator = AnimatorSet()
        val view = AnimView(activity_search_layout)
        val layoutLeftAnim = ObjectAnimator.ofObject(
            view, "LeftMargin", IntEvaluator(),
            300, 0
        )
        val layoutRightAnim = ObjectAnimator.ofObject(
            view, "RightMargin", IntEvaluator(),
            300, 0
        )
        animator.playTogether(layoutLeftAnim, layoutRightAnim)
        animator.duration = 500
        animator.start()
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
                startList(text.text.toString())
            }
            activity_search_popular.addView(text)
        }

    }

    private fun startList(text: String) {
        if (text.isNotEmpty()) {
            val intent = Intent(this, ListActivity::class.java)
            intent.putExtra(ListActivity.KEY, ListActivity.SEARCH_LIST)
            intent.putExtra("name", text)
            startActivity(intent)
        } else {
            ToastUtils.show("搜索的内容不能为空!!!")
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