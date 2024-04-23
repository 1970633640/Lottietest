package com.example.lottietest

import android.graphics.Canvas
import android.graphics.ColorFilter
import android.graphics.PixelFormat
import android.graphics.Rect
import android.graphics.drawable.Drawable
import android.view.View
import com.airbnb.lottie.LottieCompositionFactory
import com.airbnb.lottie.LottieDrawable
import com.airbnb.lottie.RenderMode

class CustomLottieDrawable(val view: View) : Drawable() {

    //constants
    private val size = 600
    private val lottieAssetName = "Lottie Logo 1.json"

    private val lottieDrawable: LottieDrawable = initLottieDrawable()

    //keep reference here, or callback will be recycled
    private var refreshCallback: Callback? = null


    init {
        this.bounds = Rect(0, 0, size, size)
        //⚠️
        //lottieDrawable.bounds = Rect(0, 0, size, size)
        lottieDrawable.resumeAnimation()
    }

    override fun draw(canvas: Canvas) {
        lottieDrawable.draw(canvas)
    }

    override fun setAlpha(alpha: Int) {}

    override fun setColorFilter(colorFilter: ColorFilter?) {}

    override fun getOpacity(): Int = PixelFormat.TRANSLUCENT

    private fun initLottieDrawable(): LottieDrawable {
        val lottieDrawable = LottieDrawable()
        this.refreshCallback = object : Callback {
            override fun invalidateDrawable(who: Drawable) {
                view.invalidate()
            }

            override fun scheduleDrawable(who: Drawable, what: Runnable, `when`: Long) {}
            override fun unscheduleDrawable(who: Drawable, what: Runnable) {}
        }
        lottieDrawable.callback = this.refreshCallback
        val result = LottieCompositionFactory.fromAssetSync(view.context, lottieAssetName).value
        lottieDrawable.composition = result
        lottieDrawable.repeatCount = LottieDrawable.INFINITE
        //⚠️
        lottieDrawable.renderMode = RenderMode.SOFTWARE
        return lottieDrawable
    }


}