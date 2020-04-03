package com.lv.wanandroid.module.home


//import com.lv.core.basedialog.LoadingView
import android.content.Intent
import androidx.recyclerview.widget.LinearLayoutManager
import com.lv.core.basedialog.LoadingView
import com.lv.wanandroid.R
import com.lv.wanandroid.base.BaseFragmentLazy
import com.lv.wanandroid.main.MainActivity
import com.lv.wanandroid.module.home.adapter.BannerImgAdapter
import com.lv.wanandroid.module.home.adapter.HomeRvAdapter
import com.lv.wanandroid.module.home.bean.Article
import com.lv.wanandroid.module.home.bean.BannerData
import com.lv.wanandroid.module.home.bean.Data
import com.lv.wanandroid.module.home.mvp.HomeContract
import com.lv.wanandroid.module.home.mvp.HomePresenter
import com.lv.wanandroid.nav.collect.MyCollect
import com.lv.wanandroid.web.AgentWebActivity
import com.youth.banner.listener.OnBannerListener
import kotlinx.android.synthetic.main.frag_home.*

@Suppress("UNCHECKED_CAST")
class HomeFragment : BaseFragmentLazy<HomeContract.View, HomeContract.Presenter>(),
    HomeContract.View {

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
        if (homeRvAdapter.data.size <= 0) {
            initBanner()
            initRv()
        }
    }

    private fun initBanner() {
        mPresenter.requestBanner()
        home_banner.setDelayTime(5000)
        home_banner.setBannerRound(8f)
    }


    private fun initRv() {
        LoadingView.showLoading("加载中", fragmentManager)
        mPresenter.requestArticle()
        home_recycler.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        home_recycler.adapter = homeRvAdapter
        homeRvAdapter.setOnItemClickListener { adapter, _, position ->
            val article: Article = adapter.data[position] as Article
            val intent = Intent(context, AgentWebActivity::class.java)
            intent.putExtra("link", article.link)
            context?.startActivity(intent)
        }
        //收藏文章
        homeRvAdapter.setOnItemLongClickListener { adapter, view, position ->
            val article: Article = adapter.data[position] as Article
            MyCollect(article.id, article.title).start(childFragmentManager, context as MainActivity)
            return@setOnItemLongClickListener true
        }

        //刷新
        refreshLayout.setOnRefreshListener {
            mPresenter.requestArticle()
            mPresenter.requestBanner()
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
        LoadingView.stopLoading()
        this.pageCount = pageCount
        this.curPage = curPage
        if (curPage == 1) {
            refreshLayout.finishRefresh(true)
            homeRvAdapter.setNewData(mutableList)
        } else {
            refreshLayout.finishLoadMore()
            homeRvAdapter.addData(mutableList)
        }
    }

    override fun resultBanner(bannerData: BannerData) {
        home_banner.adapter = BannerImgAdapter(bannerData.data)
        home_banner.setOnBannerListener(object : OnBannerListener<Data> {
            override fun onBannerChanged(position: Int) {

            }

            override fun OnBannerClick(data: Data?, position: Int) {
                val intent = Intent(context, AgentWebActivity::class.java)
                intent.putExtra("link", data?.url)
                context?.startActivity(intent)
            }
        })
    }
}