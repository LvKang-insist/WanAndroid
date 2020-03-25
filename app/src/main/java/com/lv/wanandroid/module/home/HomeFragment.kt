package com.lv.wanandroid.module.home


import com.lv.wanandroid.R
import com.lv.wanandroid.base.BaseFragment
import com.lv.wanandroid.module.home.mvp.HomeContract
import com.lv.wanandroid.module.home.mvp.HomePresenter
import kotlinx.android.synthetic.main.frag_home.*

@Suppress("UNCHECKED_CAST")
class HomeFragment : BaseFragment<HomeContract.View, HomeContract.Presenter>(), HomeContract.View {
    override fun createPresenter(): HomeContract.Presenter {
        return HomePresenter()
    }

    override fun layoutId(): Int {
        return R.layout.frag_home
    }

    override fun bindView() {
        mPresenter.requestArticle()
    }


    private fun initRv() {

    }
}