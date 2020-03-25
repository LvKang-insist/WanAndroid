package com.lv.core.utils

import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.tabs.TabLayout
import androidx.viewpager2.widget.ViewPager2
import java.lang.reflect.AccessibleObject.setAccessible
import androidx.annotation.NonNull
import androidx.annotation.Nullable
import androidx.annotation.RestrictTo
import androidx.viewpager2.widget.ViewPager2.*
import java.lang.ref.WeakReference
import java.lang.reflect.Method


class TabLayoutMediator
/**
 * Creates a TabLayoutMediator to synchronize a TabLayout and a ViewPager2 together. If `autoRefresh` is true, it will update the tabs automatically when the data set of the view
 * pager's adapter changes. The link will be established after [.attach] is called.
 *
 * @param tabLayout The tab bar to link
 * @param viewPager The view pager to link
 * @param autoRefresh If `true`, will recreate all tabs when the data set of the view
 * pager's adapter changes.
 */
    (
    private val mTabLayout: TabLayout, private val mViewPager: ViewPager2,
    private val mAutoRefresh: Boolean, private val mOnConfigureTabCallback: OnConfigureTabCallback
) {
    private var mAdapter: RecyclerView.Adapter<*>? = null
    private var mAttached: Boolean = false

    private var mOnPageChangeCallback: TabLayoutOnPageChangeCallback? = null
    private var mOnTabSelectedListener: TabLayout.OnTabSelectedListener? = null
    private var mPagerAdapterObserver: RecyclerView.AdapterDataObserver? = null

    /**
     * A callback interface that must be implemented to set the text and styling of newly created
     * tabs.
     */
    interface OnConfigureTabCallback {
        /**
         * Called to configure the tab for the page at the specified position. Typically calls
         *
         * @param tab The Tab which should be configured to represent the title of the item at the
         * given position in the data set.
         * @param position The position of the item within the adapter's data set.
         */
        fun onConfigureTab(tab: TabLayout.Tab, position: Int)
    }

    /**
     * Creates a TabLayoutMediator to synchronize a TabLayout and a ViewPager2 together. It will
     * update the tabs automatically when the data set of the view pager's adapter changes. The link
     * will be established after [.attach] is called.
     *
     * @param tabLayout The tab bar to link
     * @param viewPager The view pager to link
     */
    constructor(
        tabLayout: TabLayout, viewPager: ViewPager2,
        onConfigureTabCallback: OnConfigureTabCallback
    ) : this(tabLayout, viewPager, true, onConfigureTabCallback) {
    }

    /**
     * Link the TabLayout and the ViewPager2 together.
     * adapter.
     */
    fun attach() {
        check(!mAttached) { "TabLayoutMediator is already attached" }
        mAdapter = mViewPager.adapter
        checkNotNull(mAdapter) { "TabLayoutMediator attached before ViewPager2 has an " + "adapter" }
        mAttached = true

        // Add our custom OnPageChangeCallback to the ViewPager
        mOnPageChangeCallback = TabLayoutOnPageChangeCallback(mTabLayout)
        mViewPager.registerOnPageChangeCallback(mOnPageChangeCallback!!)

        // Now we'll add a tab selected listener to set ViewPager's current item
        mOnTabSelectedListener = ViewPagerOnTabSelectedListener(mViewPager)
        mTabLayout.addOnTabSelectedListener(mOnTabSelectedListener!!)

        // Now we'll populate ourselves from the pager adapter, adding an observer if
        // autoRefresh is enabled
        if (mAutoRefresh) {
            // Register our observer on the new adapter
            mPagerAdapterObserver = PagerAdapterObserver()
            mAdapter!!.registerAdapterDataObserver(mPagerAdapterObserver!!)
        }

        populateTabsFromPagerAdapter()

        // Now update the scroll position to match the ViewPager's current item
        mTabLayout.setScrollPosition(mViewPager.currentItem, 0f, true)
    }

    /**
     * Unlink the TabLayout and the ViewPager
     */
    fun detach() {
        mAdapter!!.unregisterAdapterDataObserver(mPagerAdapterObserver!!)
        mTabLayout.removeOnTabSelectedListener(mOnTabSelectedListener!!)
        mViewPager.unregisterOnPageChangeCallback(mOnPageChangeCallback!!)
        mPagerAdapterObserver = null
        mOnTabSelectedListener = null
        mOnPageChangeCallback = null
    }

    internal fun populateTabsFromPagerAdapter() {
        mTabLayout.removeAllTabs()

        if (mAdapter != null) {
            val adapterCount = mAdapter!!.itemCount
            for (i in 0 until adapterCount) {
                val tab = mTabLayout.newTab()
                mOnConfigureTabCallback.onConfigureTab(tab, i)
                mTabLayout.addTab(tab, false)
            }

            // Make sure we reflect the currently set ViewPager item
            if (adapterCount > 0) {
                val currItem = mViewPager.currentItem
                if (currItem != mTabLayout.selectedTabPosition) {
                    mTabLayout.getTabAt(currItem)!!.select()
                }
            }
        }
    }

    /**
     * A [ViewPager2.OnPageChangeCallback] class which contains the necessary calls back to
     * the provided [TabLayout] so that the tab position is kept in sync.
     *
     *
     * This class stores the provided TabLayout weakly, meaning that you can use [ ][ViewPager2.registerOnPageChangeCallback] without removing
     * the callback and not cause a leak.
     */
    private class TabLayoutOnPageChangeCallback internal constructor(tabLayout: TabLayout) :
        ViewPager2.OnPageChangeCallback() {
        private val mTabLayoutRef: WeakReference<TabLayout>
        private var mPreviousScrollState: Int = 0
        private var mScrollState: Int = 0

        init {
            mTabLayoutRef = WeakReference(tabLayout)
            reset()
        }

        override fun onPageScrollStateChanged(state: Int) {
            mPreviousScrollState = mScrollState
            mScrollState = state
        }

        override fun onPageScrolled(
            position: Int,
            positionOffset: Float,
            positionOffsetPixels: Int
        ) {
            val tabLayout = mTabLayoutRef.get()
            if (tabLayout != null) {
                // Only update the text selection if we're not settling, or we are settling after
                // being dragged
                val updateText =
                    mScrollState != SCROLL_STATE_SETTLING || mPreviousScrollState == SCROLL_STATE_DRAGGING
                // Update the indicator if we're not settling after being idle. This is caused
                // from a setCurrentItem() call and will be handled by an animation from
                // onPageSelected() instead.
                val updateIndicator =
                    !(mScrollState == SCROLL_STATE_SETTLING && mPreviousScrollState == SCROLL_STATE_IDLE)
                setScrollPosition(tabLayout, position, positionOffset, updateText, updateIndicator)
            }
        }

        override fun onPageSelected(position: Int) {
            val tabLayout = mTabLayoutRef.get()
            if (tabLayout != null
                && tabLayout!!.getSelectedTabPosition() != position
                && position < tabLayout!!.getTabCount()
            ) {
                // Select the tab, only updating the indicator if we're not being dragged/settled
                // (since onPageScrolled will handle that).
                val updateIndicator =
                    mScrollState == SCROLL_STATE_IDLE || mScrollState == SCROLL_STATE_SETTLING && mPreviousScrollState == SCROLL_STATE_IDLE
                selectTab(tabLayout, tabLayout!!.getTabAt(position), updateIndicator)
            }
        }

        internal fun reset() {
            mScrollState = SCROLL_STATE_IDLE
            mPreviousScrollState = mScrollState
        }
    }

    // endregion

    /**
     * A [TabLayout.OnTabSelectedListener] class which contains the necessary calls back to
     * the provided [ViewPager2] so that the tab position is kept in sync.
     */
    private class ViewPagerOnTabSelectedListener internal constructor(private val mViewPager: ViewPager2) :
        TabLayout.OnTabSelectedListener {

        override fun onTabSelected(tab: TabLayout.Tab) {
            mViewPager.setCurrentItem(tab.position, true)
        }

        override fun onTabUnselected(tab: TabLayout.Tab) {
            // No-op
        }

        override fun onTabReselected(tab: TabLayout.Tab) {
            // No-op
        }
    }

    private inner class PagerAdapterObserver internal constructor() :
        RecyclerView.AdapterDataObserver() {

        override fun onChanged() {
            populateTabsFromPagerAdapter()
        }

        override fun onItemRangeChanged(positionStart: Int, itemCount: Int) {
            populateTabsFromPagerAdapter()
        }

        override fun onItemRangeChanged(
            positionStart: Int,
            itemCount: Int, @Nullable payload: Any?
        ) {
            populateTabsFromPagerAdapter()
        }

        override fun onItemRangeInserted(positionStart: Int, itemCount: Int) {
            populateTabsFromPagerAdapter()
        }

        override fun onItemRangeRemoved(positionStart: Int, itemCount: Int) {
            populateTabsFromPagerAdapter()
        }

        override fun onItemRangeMoved(fromPosition: Int, toPosition: Int, itemCount: Int) {
            populateTabsFromPagerAdapter()
        }
    }

    companion object {

        // region Reflective calls

        // Temporarily call methods TabLayout.setScrollPosition(int, float, boolean, boolean) and
        // TabLayout.selectTab(TabLayout.Tab, boolean) through reflection, until they have been made
        // public in the Material Design Components library.

        private var sSetScrollPosition: Method? = null
        private var sSelectTab: Method? = null
        private val SET_SCROLL_POSITION_NAME =
            "TabLayout.setScrollPosition(int, float," + " boolean, boolean)"
        private val SELECT_TAB_NAME = "TabLayout.selectTab(TabLayout.Tab, boolean)"

        init {
            try {
                sSetScrollPosition = TabLayout::class.java.getDeclaredMethod(
                    "setScrollPosition",
                    Int::class.javaPrimitiveType,
                    Float::class.javaPrimitiveType,
                    Boolean::class.javaPrimitiveType,
                    Boolean::class.javaPrimitiveType
                )
                sSetScrollPosition!!.setAccessible(true)

                sSelectTab = TabLayout::class.java.getDeclaredMethod(
                    "selectTab", TabLayout.Tab::class.java,
                    Boolean::class.javaPrimitiveType
                )
                sSelectTab!!.setAccessible(true)
            } catch (e: NoSuchMethodException) {
                throw IllegalStateException("Can't reflect into method TabLayout" + ".setScrollPosition(int, float, boolean, boolean)")
            }

        }

        internal fun setScrollPosition(
            tabLayout: TabLayout, position: Int, positionOffset: Float,
            updateSelectedText: Boolean, updateIndicatorPosition: Boolean
        ) {
            try {
                if (sSetScrollPosition != null) {
                    sSetScrollPosition!!.invoke(
                        tabLayout, position, positionOffset, updateSelectedText,
                        updateIndicatorPosition
                    )
                } else {
                    throwMethodNotFound(SET_SCROLL_POSITION_NAME)
                }
            } catch (e: Exception) {
                throwInvokeFailed(SET_SCROLL_POSITION_NAME)
            }

        }

        internal fun selectTab(
            tabLayout: TabLayout,
            tab: TabLayout.Tab?,
            updateIndicator: Boolean
        ) {
            try {
                if (sSelectTab != null) {
                    sSelectTab!!.invoke(tabLayout, tab, updateIndicator)
                } else {
                    throwMethodNotFound(SELECT_TAB_NAME)
                }
            } catch (e: Exception) {
                throwInvokeFailed(SELECT_TAB_NAME)
            }

        }

        private fun throwMethodNotFound(method: String) {
            throw IllegalStateException("Method $method not found")
        }

        private fun throwInvokeFailed(method: String) {
            throw IllegalStateException("Couldn't invoke method $method")
        }
    }
}