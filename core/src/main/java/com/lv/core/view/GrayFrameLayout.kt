package com.lv.core.view

import android.content.Context
import android.graphics.Canvas
import android.graphics.ColorMatrix
import android.graphics.ColorMatrixColorFilter
import android.graphics.Paint
import android.util.AttributeSet
import android.widget.FrameLayout

/**
 * 灰度的布局
 */
class GrayFrameLayout : FrameLayout {

    private val mPaint = Paint()

    constructor(context: Context) : this(context, null)
    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 1)
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context, attrs, defStyleAttr
    ) {
        val cm = ColorMatrix()
        cm.setSaturation(0f)
        mPaint.setColorFilter(ColorMatrixColorFilter(cm))
    }

    override fun draw(canvas: Canvas?) {
        canvas?.saveLayer(null, mPaint, Canvas.ALL_SAVE_FLAG)
        super.draw(canvas)
        canvas?.restore()
    }

    override fun dispatchDraw(canvas: Canvas?) {
        canvas?.saveLayer(null, mPaint, Canvas.ALL_SAVE_FLAG)
        super.dispatchDraw(canvas)
        canvas?.restore()
    }


}