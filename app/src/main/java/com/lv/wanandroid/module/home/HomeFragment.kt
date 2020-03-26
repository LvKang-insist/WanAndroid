package com.lv.wanandroid.module.home


import android.content.Intent
import androidx.recyclerview.widget.LinearLayoutManager
import com.lv.core.utils.DividerItemDecoration
import com.lv.wanandroid.R
import com.lv.wanandroid.base.BaseFragment
import com.lv.wanandroid.module.home.adapter.BannerImgAdapter
import com.lv.wanandroid.module.home.adapter.HomeRvAdapter
import com.lv.wanandroid.module.home.bean.Article
import com.lv.wanandroid.module.home.bean.BannerData
import com.lv.wanandroid.module.home.mvp.HomeContract
import com.lv.wanandroid.module.home.mvp.HomePresenter
import com.lv.wanandroid.web.WebActivity
import kotlinx.android.synthetic.main.frag_home.*

@Suppress("UNCHECKED_CAST")
class HomeFragment : BaseFragment<HomeContract.View, HomeContract.Presenter>(), HomeContract.View {

    private var pageCount: Int = 0
    private var curPage: Int = 0
    private val homeRvAdapter by lazy {
        HomeRvAdapter(R.layout.home_rv_item)
    }

    override fun createPresenter(): HomeContract.Presenter {
        return HomePresenter()
    }

    override fun layoutId(): Int {
        return R.layout.frag_home
    }


    override fun bindView() {
        home_recycler.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        home_recycler.addItemDecoration(
            DividerItemDecoration(context!!, DividerItemDecoration.VERTICAL_LIST)
        )
        home_recycler.adapter = homeRvAdapter
        initRv()
        initBanner()
    }

    private fun initBanner() {
        mPresenter.requestBanner()
        refreshLayout.requestDisallowInterceptTouchEvent(true)
    }


    private fun initRv() {
        mPresenter.requestArticle()
        homeRvAdapter.setOnItemClickListener { adapter, view, position ->
            val article: Article = adapter.data[position] as Article
            val intent = Intent(context, WebActivity::class.java)
            intent.putExtra("link", article.link)
            intent.putExtra("projectLink", article.projectLink)
            context?.startActivity(intent)
        }
        //刷新
        refreshLayout.setOnRefreshListener {
            mPresenter.requestArticle()
        }
        //        加载
        refreshLayout.setOnLoadMoreListener {
            if (++curPage < pageCount) {
                mPresenter.requestArticlePage(curPage)
            } else {
                refreshLayout.finishRefreshWithNoMoreData()
            }
        }
    }

    override fun resultArticle(pageCount: Int, curPage: Int, mutableList: MutableList<Article?>) {
        this.pageCount = pageCount
        this.curPage = curPage
        if (curPage == 1) {
            homeRvAdapter.setNewData(mutableList)
            refreshLayout.finishRefresh(true);
        } else {
            homeRvAdapter.addData(mutableList)
            refreshLayout.finishLoadMore()
        }
    }

    override fun resultBanner(bannerData: BannerData) {
        home_banner.adapter = BannerImgAdapter(bannerData.data)
        val intArray = IntArray(2)
        home_banner.getLocationInWindow(intArray)
    }
}