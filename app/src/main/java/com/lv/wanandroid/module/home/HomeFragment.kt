package com.lv.wanandroid.module.home


import android.content.Intent
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.hjq.toast.ToastUtils
import com.lv.core.utils.DividerItemDecoration
import com.lv.wanandroid.R
import com.lv.wanandroid.base.BaseFragment
import com.lv.wanandroid.module.home.adapter.BannerImgAdapter
import com.lv.wanandroid.module.home.adapter.HomeRvAdapter
import com.lv.wanandroid.module.home.bean.Article
import com.lv.wanandroid.module.home.bean.BannerData
import com.lv.wanandroid.module.home.bean.Data
import com.lv.wanandroid.module.home.mvp.HomeContract
import com.lv.wanandroid.module.home.mvp.HomePresenter
import com.lv.wanandroid.web.WebActivity
import com.youth.banner.listener.OnBannerListener
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
        home_banner.setDelayTime(5000)
        home_banner.setBannerRound(8f)
        //设置一屏多页的效果
        home_banner.viewPager2.offscreenPageLimit = 1
        val recyclerView = home_banner.viewPager2.getChildAt(0) as RecyclerView
        val padding =
            resources.getDimensionPixelOffset(R.dimen._12) +
                    resources.getDimensionPixelOffset(R.dimen._12)
        recyclerView.setPadding(padding, 0, padding, 0)
        recyclerView.clipToPadding = false;

    }


    private fun initRv() {
        mPresenter.requestArticle()
        homeRvAdapter.setOnItemClickListener { adapter, view, position ->
            val article: Article = adapter.data[position] as Article
            val intent = Intent(context, WebActivity::class.java)
            intent.putExtra("link", article.link)
            intent.putExtra("title", article.title)
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

        home_banner.setOnBannerListener(object : OnBannerListener<Data> {
            override fun onBannerChanged(position: Int) {

            }

            override fun OnBannerClick(data: Data?, position: Int) {
                val intent = Intent(context, WebActivity::class.java)
                intent.putExtra("link", data?.url)
                context?.startActivity(intent)
            }
        })
    }
}