package com.lv.wanandroid.module.square

import android.view.LayoutInflater
import androidx.appcompat.widget.AppCompatTextView
import com.lv.core.basedialog.LoadingView
import com.lv.core.utils.load
import com.lv.wanandroid.R
import com.lv.wanandroid.base.BaseFragmentLazy
import com.lv.wanandroid.module.square.bean.Chapters
import com.lv.wanandroid.module.square.mvp.SquareContract
import com.lv.wanandroid.module.square.mvp.SquarePresenter
import kotlinx.android.synthetic.main.frag_square.*

class SquareFragment : BaseFragmentLazy<SquareContract.View, SquareContract.Presenter>(),
    SquareContract.View {

    override fun createPresenter(): SquareContract.Presenter {
        return SquarePresenter()
    }

    override fun layoutId(): Int {
        return R.layout.frag_square
    }

    override fun bindView() {
        LoadingView.showLoading("加载中", fragmentManager)
        load(
            "https://cn.bing.com/th?id=OHR.CarrickSpring_ZH-CN7085146237_1920x1080.jpg",
            frag_square_img
        )
        mPresenter.requestWx()
    }

    override fun resultWx(data: List<Chapters.Data>) {
        data.forEach {
            val tv: AppCompatTextView = LayoutInflater.from(context)
                .inflate(
                    R.layout.frag_square_tv_item, frag_square_hor_layout, false
                ) as AppCompatTextView
            tv.text = it.name
            frag_square_hor_layout.addView(tv)
        }
        LoadingView.stopLoading()
    }

}