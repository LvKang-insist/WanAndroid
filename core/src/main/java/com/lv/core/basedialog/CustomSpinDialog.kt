package com.lv.core.basedialog

import android.animation.ObjectAnimator
import android.animation.TimeInterpolator
import android.animation.ValueAnimator
import android.view.View
import android.view.Window
import android.view.animation.LinearInterpolator
import android.widget.ImageView
import com.lv.core.R

/**
 * Created by Petterp
 * on 2019-12-16
 * Function:自定义图片旋转dialog
 */
class CustomSpinDialog(
    view: Any?,
    alpha: Float,
    autoDismiss: Boolean,
    cancelable: Boolean,
    animation: Int,
    gravity: Int
) : BaseFragDialog(view, alpha, autoDismiss, cancelable, animation, gravity) {
    private var width = 200
    private var height = 200
    private var time = 2000
    private var imgResource = R.drawable.ic_dialog_spin
    fun setWidth(width: Int): CustomSpinDialog {
        this.width = width
        return this
    }

    fun setHeight(height: Int): CustomSpinDialog {
        this.height = height
        return this
    }

    override fun initView(view: View?) {
        val imageView =
            view!!.findViewById<ImageView>(R.id.img_dialog_spin)
        imageView.setImageResource(imgResource)
        val objectAnimator = ObjectAnimator.ofFloat(imageView, "rotation", 0f, 360f)
        objectAnimator.repeatCount = ValueAnimator.INFINITE
        objectAnimator.duration = time.toLong()
        objectAnimator.interpolator = LinearInterpolator() as TimeInterpolator?
        objectAnimator.start()
        //取消背景遮罩
        setBackgroundDimEnabled(false)
    }

    fun setDuration(time: Int): CustomSpinDialog {
        this.time = time
        return this
    }

    fun setResource(resource: Int): CustomSpinDialog {
        imgResource = resource
        return this
    }

    override fun setWindow(window: Window?) {
        super.setWindow(window)
        window!!.attributes.width = width
        window.attributes.height = height
    }

    companion object {
        fun customSpinBuilder(): DialogBuilder<CustomSpinDialog> {
            return DialogBuilder(CustomSpinDialog::class.java)
        }
    }
}