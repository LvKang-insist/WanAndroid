package com.lv.core.utils

import androidx.viewpager2.widget.ViewPager2
import net.lucode.hackware.magicindicator.MagicIndicator

class ViewPage2Helpter {
    companion object {
        fun bind(magicIndicator: MagicIndicator, viewPager2: ViewPager2) {
            viewPager2.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {

                override fun onPageScrolled(
                    position: Int, positionOffset: Float, positionOffsetPixels: Int
                ) {
                    magicIndicator.onPageScrolled(position, positionOffset, positionOffsetPixels)
                }
                override fun onPageSelected(position: Int) {
                    magicIndicator.onPageSelected(position)
                }
                override fun onPageScrollStateChanged(state: Int) {
                    magicIndicator.onPageScrollStateChanged(state)
                }
            })
        }
    }
}