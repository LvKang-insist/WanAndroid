package com.lv.wanandroid.module.system

import android.content.Context
import android.view.View

import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.PagerSnapHelper
import androidx.recyclerview.widget.RecyclerView

class ViewPagerLayoutManager : LinearLayoutManager {
    private var mPagerSnapHelper: PagerSnapHelper? = null
    private var mOnViewPagerListener: OnViewPagerListener? = null
    private var mRecyclerView: RecyclerView? = null
    private var mDrift: Int = 0//位移，用来判断移动方向

    private val mChildAttachStateChangeListener =
        object : RecyclerView.OnChildAttachStateChangeListener {
            override fun onChildViewAttachedToWindow(view: View) {
                if (mOnViewPagerListener != null && childCount == 1) {
                    mOnViewPagerListener!!.onInitComplete()
                }
            }

            override fun onChildViewDetachedFromWindow(view: View) {
                if (mOnViewPagerListener != null) {
                    if (mDrift >= 0) {
                        mOnViewPagerListener!!.onPageRelease(true, getPosition(view))
                    } else {
                        mOnViewPagerListener!!.onPageRelease(false, getPosition(view))
                    }
                }

            }
        }

    constructor(context: Context, orientation: Int) : super(context, orientation, false) {
        init()
    }

    constructor(context: Context, orientation: Int, reverseLayout: Boolean) : super(
        context,
        orientation,
        reverseLayout
    ) {
        init()
    }

    private fun init() {
        mPagerSnapHelper = PagerSnapHelper()
    }

    override fun onAttachedToWindow(view: RecyclerView?) {
        super.onAttachedToWindow(view)

        mPagerSnapHelper!!.attachToRecyclerView(view)
        this.mRecyclerView = view
        mRecyclerView!!.addOnChildAttachStateChangeListener(mChildAttachStateChangeListener)
    }


    /**
     * 滑动状态的改变
     * 缓慢拖拽-> SCROLL_STATE_DRAGGING
     * 快速滚动-> SCROLL_STATE_SETTLING
     * 空闲状态-> SCROLL_STATE_IDLE
     *
     * @param state
     */
    override fun onScrollStateChanged(state: Int) {
        when (state) {
            RecyclerView.SCROLL_STATE_IDLE -> {
                val viewIdle = mPagerSnapHelper!!.findSnapView(this)
                if (viewIdle != null) {
                    val positionIdle = getPosition(viewIdle)
                    val childCount = childCount
                    if (mOnViewPagerListener != null && getChildCount() <= 2) {
                        mOnViewPagerListener!!.onPageSelected(
                            positionIdle,
                            positionIdle == itemCount - 1
                        )
                    }
                }
            }
            RecyclerView.SCROLL_STATE_DRAGGING -> {
            }
            RecyclerView.SCROLL_STATE_SETTLING -> {
            }
            else -> {
            }
        }
    }


    /**
     * 监听竖直方向的相对偏移量
     *
     * @param dy
     * @param recycler
     * @param state
     * @return
     */
    override fun scrollVerticallyBy(
        dy: Int,
        recycler: RecyclerView.Recycler?,
        state: RecyclerView.State?
    ): Int {
        this.mDrift = dy
        return super.scrollVerticallyBy(dy, recycler, state)
    }


    /**
     * 监听水平方向的相对偏移量
     *
     * @param dx
     * @param recycler
     * @param state
     * @return
     */
    override fun scrollHorizontallyBy(
        dx: Int,
        recycler: RecyclerView.Recycler?,
        state: RecyclerView.State?
    ): Int {
        this.mDrift = dx
        return super.scrollHorizontallyBy(dx, recycler, state)
    }

    /**
     * if return >= 0 snap is exist
     * if return < 0 snap is not exist
     *
     * @return
     */
    fun findSnapPosition(): Int {
        val viewIdle = mPagerSnapHelper!!.findSnapView(this)
        return if (viewIdle != null) {
            getPosition(viewIdle)
        } else {
            -1
        }
    }

    /**
     * 设置监听
     *
     * @param listener
     */
    fun setOnViewPagerListener(listener: OnViewPagerListener) {
        this.mOnViewPagerListener = listener
    }

    interface OnViewPagerListener {
        /**
         * 初始化第一个View
         */
        fun onInitComplete()


        /**
         * 选中的监听以及判断是否滑动到底部
         *
         * @param position
         * @param isBottom
         */
        fun onPageSelected(position: Int, isBottom: Boolean)


        /**
         * 释放的监听
         *
         * @param isNext
         * @param position
         */
        fun onPageRelease(isNext: Boolean, position: Int)
    }

    companion object {
        private val TAG = "ViewPagerLayoutManager"
    }
}